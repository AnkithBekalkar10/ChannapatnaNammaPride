package com.channapatna.nammapride.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseChannapatnaRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val storyGenerator: StoryGenerator = StaticStoryGenerator()
) : ChannapatnaRepository {
    override suspend fun verifyToy(toyId: String, languageCode: String): VerifiedToy? {
        val toy = db.collection("toys")
            .whereEqualTo("toyId", toyId)
            .limit(1)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.toToy()
            ?: return null

        val artisan = db.collection("artisans")
            .whereEqualTo("artisanId", toy.artisanId)
            .limit(1)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.toArtisan()
            ?: return null

        val workshop = db.collection("workshops")
            .whereEqualTo("workshopId", artisan.workshopId)
            .limit(1)
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.toWorkshop()
            ?: return null

        return VerifiedToy(
            toy = toy,
            artisan = artisan,
            workshop = workshop,
            story = storyGenerator.generateStory(toy, artisan, workshop, "en"),
            storyKannada = storyGenerator.generateStory(toy, artisan, workshop, "kn")
        )
    }

    override suspend fun toys(): List<Toy> {
        return db.collection("toys")
            .get()
            .await()
            .documents
            .mapNotNull { it.toToy() }
    }

    override suspend fun artisans(): List<Artisan> {
        return db.collection("artisans")
            .get()
            .await()
            .documents
            .mapNotNull { it.toArtisan() }
    }

    override suspend fun workshops(): List<Workshop> {
        return db.collection("workshops")
            .get()
            .await()
            .documents
            .mapNotNull { it.toWorkshop() }
    }

    override suspend fun educationSteps(): List<EducationStep> {
        return db.collection("educational_content")
            .orderBy("stepNumber")
            .get()
            .await()
            .documents
            .mapNotNull { document ->
                val title = document.getString("title") ?: return@mapNotNull null
                val description = document.getString("description") ?: return@mapNotNull null
                EducationStep(
                    title = title,
                    description = description,
                    accentColor = processAccent(document.getLong("stepNumber")?.toInt() ?: 0),
                    titleKannada = document.getString("titleKannada") ?: "",
                    descriptionKannada = document.getString("descriptionKannada") ?: ""
                )
            }
    }

    override suspend fun adminSignIn(email: String, password: String): Result<Unit> {
        return runCatching {
            auth.signInWithEmailAndPassword(email.trim(), password).await()
            Unit
        }
    }

    override fun adminSignOut() {
        auth.signOut()
    }

    override fun isAdminSignedIn(): Boolean = auth.currentUser != null

    override suspend fun addArtisan(artisan: Artisan): Result<Unit> {
        return runCatching {
            if (auth.currentUser == null) {
                error("Admin login required")
            }
            db.collection("artisans")
                .document(artisan.artisanId)
                .set(
                    mapOf(
                        "artisanId" to artisan.artisanId,
                        "name" to artisan.name,
                        "nameKannada" to artisan.nameKannada,
                        "village" to artisan.village,
                        "experience" to artisan.experience,
                        "specialization" to artisan.specialization,
                        "bio" to artisan.bio,
                        "bioKannada" to artisan.bioKannada,
                        "workshopId" to artisan.workshopId
                    )
                )
                .await()
            Unit
        }
    }

    override suspend fun addWorkshop(workshop: Workshop): Result<Unit> {
        return runCatching {
            if (auth.currentUser == null) {
                error("Admin login required")
            }
            db.collection("workshops")
                .document(workshop.workshopId)
                .set(
                    mapOf(
                        "workshopId" to workshop.workshopId,
                        "name" to workshop.name,
                        "address" to workshop.address,
                        "phone" to workshop.phone,
                        "timings" to workshop.timings,
                        "latitude" to workshop.latitude,
                        "longitude" to workshop.longitude
                    )
                )
                .await()
            Unit
        }
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toToy(): Toy? {
        val toyId = getString("toyId") ?: id.takeIf { it.isNotBlank() } ?: return null
        return Toy(
            toyId = toyId,
            name = getString("name") ?: return null,
            category = getString("category") ?: "General",
            artisanId = getString("artisanId") ?: return null,
            creationDate = getString("creationDate") ?: "Unknown",
            price = getLong("price")?.toInt() ?: 0,
            giTagNumber = getString("giTagNumber") ?: "GI-2005-00063",
            colors = defaultToyColors(category = getString("category"))
        )
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toArtisan(): Artisan? {
        val artisanId = getString("artisanId") ?: id.takeIf { it.isNotBlank() } ?: return null
        return Artisan(
            artisanId = artisanId,
            name = getString("name") ?: return null,
            nameKannada = getString("nameKannada") ?: "",
            village = getString("village") ?: "Channapatna",
            experience = getLong("experience")?.toInt() ?: 0,
            specialization = getString("specialization") ?: "Wooden toys",
            bio = getString("bio") ?: "",
            bioKannada = getString("bioKannada") ?: "",
            workshopId = getString("workshopId") ?: return null
        )
    }

    private fun com.google.firebase.firestore.DocumentSnapshot.toWorkshop(): Workshop? {
        val workshopId = getString("workshopId") ?: id.takeIf { it.isNotBlank() } ?: return null
        return Workshop(
            workshopId = workshopId,
            name = getString("name") ?: return null,
            address = getString("address") ?: "Channapatna, Karnataka",
            phone = getString("phone") ?: "",
            timings = getString("timings") ?: "",
            latitude = getDouble("latitude") ?: 12.6518,
            longitude = getDouble("longitude") ?: 77.2069
        )
    }

    private fun defaultToyColors(category: String?): List<Long> {
        return when (category) {
            "Puzzles" -> listOf(0xFF2F80ED, 0xFF27AE60, 0xFFF2994A)
            "Traditional Dolls" -> listOf(0xFF9B51E0, 0xFFF2C94C, 0xFFEB5757)
            "Animal Figurines" -> listOf(0xFFFF8A00, 0xFF111827, 0xFFFFFFFF)
            else -> listOf(0xFFE53935, 0xFFFFD54F, 0xFF00A896)
        }
    }

    private fun processAccent(stepNumber: Int): Long {
        return listOf(0xFF00A896, 0xFFE53935, 0xFFF2994A, 0xFF2F80ED).getOrElse(stepNumber - 1) { 0xFF00A896 }
    }
}
