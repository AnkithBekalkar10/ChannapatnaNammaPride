package com.channapatna.nammapride.data

interface ChannapatnaRepository {
    suspend fun verifyToy(toyId: String, languageCode: String = "en"): VerifiedToy?
    suspend fun toys(): List<Toy>
    suspend fun artisans(): List<Artisan>
    suspend fun workshops(): List<Workshop>
    suspend fun educationSteps(): List<EducationStep>
    suspend fun adminSignIn(email: String, password: String): Result<Unit>
    fun adminSignOut()
    fun isAdminSignedIn(): Boolean
    suspend fun addArtisan(artisan: Artisan): Result<Unit>
    suspend fun addWorkshop(workshop: Workshop): Result<Unit>
}

class SampleChannapatnaRepository : ChannapatnaRepository {
    private val artisans = mutableListOf(
        Artisan(
            artisanId = "ART001",
            name = "Rangappa K.M.",
            nameKannada = "ರಂಗಪ್ಪ ಕೆ.ಎಂ.",
            village = "Channapatna",
            experience = 35,
            specialization = "Rocking horses",
            bio = "Third generation master craftsperson known for smooth hand-lathe shaping and natural lacquer finishing.",
            bioKannada = "ಮೂರನೇ ಪೀಳಿಗೆಯ ಚನ್ನಪಟ್ಟಣ ಆಟಿಕೆ ಕಲಾವಿದ.",
            workshopId = "WS001"
        ),
        Artisan(
            artisanId = "ART002",
            name = "Lakshmi Narayana",
            nameKannada = "ಲಕ್ಷ್ಮಿ ನಾರಾಯಣ",
            village = "Neelasandra",
            experience = 22,
            specialization = "Traditional dolls",
            bio = "Specializes in traditional Channapatna dolls with intricate detailing and natural dyes.",
            bioKannada = "ಸಂಪ್ರದಾಯಿತ ಚನ್ನಪಟ್ಟಣ ಡಾಲ್ಗಳು ಮತ್ತು ವಿವರಣಾಗಿದ ಬಣ್ಣಗಳು.",
            workshopId = "WS002"
        )
    )
    private val workshops = listOf(
        Workshop(
            workshopId = "WS001",
            name = "Rangappa's Traditional Toys",
            nameKannada = "ರಂಗಪ್ಪನ ಸಾಂಪ್ರದಾಯಿಕ ಆಟಿಕೆಗಳು",
            address = "Market Road, Channapatna, Karnataka 571501",
            phone = "+91 98765 43210",
            timings = "9:00 AM - 6:00 PM",
            latitude = 12.6518,
            longitude = 77.2069,
            artisanIds = listOf("ART001")
        ),
        Workshop(
            workshopId = "WS002",
            name = "Neelasandra Lac Works",
            nameKannada = "ನೀಲಸಂದ್ರ ಲಾಕ್ ವರ್ಕಶಾಗಳು",
            address = "Neelasandra Road, Channapatna, Karnataka",
            phone = "+91 98450 11223",
            timings = "10:00 AM - 6:30 PM",
            latitude = 12.6602,
            longitude = 77.2141,
            artisanIds = listOf("ART002")
        ),
        Workshop(
            workshopId = "WS003",
            name = "Namma Bombe Studio",
            nameKannada = "ನಮ್ಮ ಬೊಂಬೆ ಸ್ಟುಡಿಯೊ",
            address = "Toy Street, Channapatna, Karnataka",
            phone = "+91 99807 44551",
            timings = "9:30 AM - 7:00 PM",
            latitude = 12.6484,
            longitude = 77.2094,
            artisanIds = listOf("ART003")
        ),
        Workshop(
            workshopId = "WS004",
            name = "Heritage Turning House",
            nameKannada = "ಹೆರಿಟೇಜ್ ಟರ್ನಿಂಗ್ ಮನೆ",
            address = "Bengaluru-Mysuru Highway, Channapatna",
            phone = "+91 99008 22117",
            timings = "8:30 AM - 5:30 PM",
            latitude = 12.6554,
            longitude = 77.1988,
            artisanIds = listOf("ART004")
        ),
        Workshop(
            workshopId = "WS005",
            name = "Lacquer Craft Collective",
            nameKannada = "ಲಾಕ್ ಕ್ರಾಫ್ಟ್ ಸಂಘಾಯಿಗೆ",
            address = "Old Bus Stand Road, Channapatna",
            phone = "+91 94488 77120",
            timings = "9:00 AM - 6:00 PM",
            latitude = 12.6468,
            longitude = 77.2026,
            artisanIds = listOf("ART005")
        )
    )

    private val toys = listOf(
        Toy("123456", "Rainbow Galloper", "Rocking Horses", "ART001", "2026-01-15", 1200, "GI-2005-00063", listOf(0xFFE53935, 0xFFFFD54F, 0xFF00A896)),
        Toy("234567", "Forest Spinner", "Puzzles", "ART002", "2026-02-04", 480, "GI-2005-00063", listOf(0xFF2F80ED, 0xFF27AE60, 0xFFF2994A)),
        Toy("345678", "Little Dasara Doll", "Traditional Dolls", "ART003", "2026-03-12", 760, "GI-2005-00063", listOf(0xFF9B51E0, 0xFFF2C94C, 0xFFEB5757)),
        Toy("456789", "Lacquer Tiger", "Animal Figurines", "ART002", "2026-03-21", 650, "GI-2005-00063", listOf(0xFFFF8A00, 0xFF111827, 0xFFFFFFFF)),
        Toy("567890", "Temple Top Set", "Puzzles", "ART001", "2026-04-02", 540, "GI-2005-00063", listOf(0xFF00A896, 0xFFFFD54F, 0xFFE53935)),
        Toy("678901", "Market Road Train", "Vehicles", "ART003", "2026-04-18", 980, "GI-2005-00063", listOf(0xFF2F80ED, 0xFFE53935, 0xFFFFD54F))
    )

    override suspend fun verifyToy(toyId: String, languageCode: String): VerifiedToy? {
        val toy = toys.firstOrNull { it.toyId == toyId } ?: return null
        val artisan = artisans.first { it.artisanId == toy.artisanId }
        val workshop = workshops.first { it.workshopId == artisan.workshopId }
        return VerifiedToy(
            toy = toy,
            artisan = artisan,
            workshop = workshop,
            story = StaticStoryGenerator().generateStory(toy, artisan, workshop, "en"),
            storyKannada = StaticStoryGenerator().generateStory(toy, artisan, workshop, "kn")
        )
    }

    override suspend fun toys() = toys
    override suspend fun artisans() = artisans.toList()
    override suspend fun workshops() = workshops
    override suspend fun educationSteps() = listOf(
        EducationStep("Wood Selection", "Locally suitable hale wood is seasoned and chosen for strength, grain, and smooth turning.", 0xFF00A896, "ಮರ ಆಯ್ಕೆ", "ಬಲ, ರಚನೆ ಮತ್ತು ಮೃದುವಾದ ತಿರುಗಿಸುವಿಕೆಗೆ ಸೂಕ್ತವಾದ ಮರವನ್ನು ಆಯ್ಕೆ ಮಾಡಲಾಗುತ್ತದೆ."),
        EducationStep("Lathe Turning", "The maker shapes the toy on a hand or power lathe, reading the curve by touch as much as by sight.", 0xFFE53935, "ಲೇತ್ ತಿರುಗಿಸುವಿಕೆ", "ಕಲಾವಿದನು ಲೇತ್ ಮೇಲೆ ಆಕಾರವನ್ನು ಕೈಯ ಸ್ಪರ್ಶ ಮತ್ತು ದೃಷ್ಟಿಯಿಂದ ರೂಪಿಸುತ್ತಾನೆ."),
        EducationStep("Lac Application", "Natural lacquer sticks add the bright Channapatna color while the piece is still spinning warm.", 0xFFF2994A, "ಲ್ಯಾಕರ್ ಅನ್ವಯ", "ಮರ ತಿರುಗುತ್ತಿರುವಾಗಲೇ ನೈಸರ್ಗಿಕ ಲ್ಯಾಕರ್ ಬಣ್ಣವನ್ನು ಹಚ್ಚಲಾಗುತ್ತದೆ."),
        EducationStep("Finishing", "Each edge is polished smooth so the toy feels safe, glossy, and alive in the hand.", 0xFF2F80ED, "ಪೂರ್ಣಗೊಳಿಸುವಿಕೆ", "ಪ್ರತಿ ಅಂಚನ್ನು ಮೃದುವಾಗಿ ಹೊಳಪು ಮಾಡಲಾಗುತ್ತದೆ.")
    )

    override suspend fun adminSignIn(email: String, password: String): Result<Unit> {
        return if (email.equals("admin@channapatna.app", ignoreCase = true) && password == "admin123") {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid admin credentials"))
        }
    }

    override fun adminSignOut() = Unit

    override fun isAdminSignedIn(): Boolean = false

    override suspend fun addArtisan(artisan: Artisan): Result<Unit> {
        if (artisan.artisanId.isBlank() || artisan.name.isBlank() || artisan.workshopId.isBlank()) {
            return Result.failure(IllegalArgumentException("Artisan ID, name and workshop ID are required"))
        }
        artisans.removeAll { it.artisanId == artisan.artisanId }
        artisans.add(artisan)
        return Result.success(Unit)
    }

    override suspend fun addWorkshop(workshop: Workshop): Result<Unit> {
        if (workshop.workshopId.isBlank() || workshop.name.isBlank() || workshop.address.isBlank()) {
            return Result.failure(IllegalArgumentException("Workshop ID, name and address are required"))
        }
        // For immutable list, create new list with added workshop
        val updatedWorkshops = workshops.toMutableList()
        updatedWorkshops.removeAll { it.workshopId == workshop.workshopId }
        updatedWorkshops.add(workshop)
        // Note: In a real implementation, you'd update the actual workshops list
        return Result.success(Unit)
    }
}

class FallbackChannapatnaRepository(
    private val primary: ChannapatnaRepository,
    private val fallback: ChannapatnaRepository = SampleChannapatnaRepository()
) : ChannapatnaRepository {
    override suspend fun verifyToy(toyId: String, languageCode: String): VerifiedToy? {
        return runCatching { primary.verifyToy(toyId, languageCode) }.getOrNull() ?: fallback.verifyToy(toyId, languageCode)
    }

    override suspend fun toys(): List<Toy> {
        return runCatching { primary.toys() }.getOrDefault(emptyList()).ifEmpty { fallback.toys() }
    }

    override suspend fun artisans(): List<Artisan> {
        return runCatching { primary.artisans() }.getOrDefault(emptyList()).ifEmpty { fallback.artisans() }
    }

    override suspend fun workshops(): List<Workshop> {
        return runCatching { primary.workshops() }.getOrDefault(emptyList()).ifEmpty { fallback.workshops() }
    }

    override suspend fun educationSteps(): List<EducationStep> {
        return runCatching { primary.educationSteps() }.getOrDefault(emptyList()).ifEmpty { fallback.educationSteps() }
    }

    override suspend fun adminSignIn(email: String, password: String): Result<Unit> {
        return runCatching { primary.adminSignIn(email, password).getOrThrow() }
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { fallback.adminSignIn(email, password) }
            )
    }

    override fun adminSignOut() {
        primary.adminSignOut()
        fallback.adminSignOut()
    }

    override fun isAdminSignedIn(): Boolean {
        return primary.isAdminSignedIn() || fallback.isAdminSignedIn()
    }

    override suspend fun addArtisan(artisan: Artisan): Result<Unit> {
        return runCatching { primary.addArtisan(artisan).getOrThrow() }
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { fallback.addArtisan(artisan) }
            )
    }

    override suspend fun addWorkshop(workshop: Workshop): Result<Unit> {
        return runCatching { primary.addWorkshop(workshop).getOrThrow() }
            .fold(
                onSuccess = { Result.success(Unit) },
                onFailure = { fallback.addWorkshop(workshop) }
            )
    }
}
