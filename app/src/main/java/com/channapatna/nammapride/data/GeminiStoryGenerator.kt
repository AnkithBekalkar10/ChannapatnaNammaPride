package com.channapatna.nammapride.data

import com.google.ai.client.generativeai.GenerativeModel

interface StoryGenerator {
    suspend fun generateStory(toy: Toy, artisan: Artisan, workshop: Workshop, languageCode: String): String
}

class StaticStoryGenerator : StoryGenerator {
    override suspend fun generateStory(toy: Toy, artisan: Artisan, workshop: Workshop, languageCode: String): String {
        return if (languageCode == "kn") {
            "${toy.name} ಆಟಿಕೆ ${artisan.name} ಅವರ ಕೈಚಳಕವನ್ನು ಹೊತ್ತಿದೆ. ${workshop.name} ನಲ್ಲಿ ತಯಾರಾದ ಈ GI ಗುರುತಿನ ಆಟಿಕೆ ಚನ್ನಪಟ್ಟಣದ ಸಂರಕ್ಷಿತ ಮರದ ಆಟಿಕೆ ಪರಂಪರೆಯೊಂದಿಗಿನ ನಿಜವಾದ ಸಂಪರ್ಕವನ್ನು ತೋರಿಸುತ್ತದೆ."
        } else {
            "${toy.name} carries ${artisan.name}'s hand-finished lacquer work from ${artisan.village}. Its GI tag confirms the toy belongs to Channapatna's protected craft lineage, shaped with local skill and made to be kept, gifted, and remembered."
        }
    }
}

class GeminiStoryGenerator(
    apiKey: String,
    private val fallback: StoryGenerator = StaticStoryGenerator()
) : StoryGenerator {
    private val model = apiKey
        .takeIf { it.isNotBlank() }
        ?.let { GenerativeModel(modelName = "gemini-1.5-flash", apiKey = it) }

    override suspend fun generateStory(toy: Toy, artisan: Artisan, workshop: Workshop, languageCode: String): String {
        val generativeModel = model ?: return fallback.generateStory(toy, artisan, workshop, languageCode)
        val language = if (languageCode == "kn") "Kannada" else "English"
        val prompt = """
            Write a warm, culturally respectful 90-word story in $language for an authenticated GI-tagged Channapatna wooden toy.
            Toy: ${toy.name}
            Category: ${toy.category}
            GI tag: ${toy.giTagNumber}
            Artisan: ${artisan.name}
            Village: ${artisan.village}
            Workshop: ${workshop.name}
            Mention craft, lacquer, heritage, and the maker. Do not invent personal facts.
        """.trimIndent()

        return runCatching {
            generativeModel.generateContent(prompt).text?.trim()
        }.getOrNull().takeUnless { it.isNullOrBlank() }
            ?: fallback.generateStory(toy, artisan, workshop, languageCode)
    }
}
