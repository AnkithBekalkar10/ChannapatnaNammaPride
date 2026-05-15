package com.channapatna.nammapride.data

data class Toy(
    val toyId: String,
    val name: String,
    val category: String,
    val artisanId: String,
    val creationDate: String,
    val price: Int,
    val giTagNumber: String,
    val colors: List<Long>
)

data class Artisan(
    val artisanId: String,
    val name: String,
    val nameKannada: String,
    val village: String,
    val experience: Int,
    val specialization: String,
    val bio: String,
    val bioKannada: String = "",
    val workshopId: String
)

data class Workshop(
    val workshopId: String,
    val name: String,
    val nameKannada: String = "",
    val address: String,
    val phone: String,
    val timings: String,
    val latitude: Double,
    val longitude: Double,
    val artisanIds: List<String> = emptyList()
)

data class EducationStep(
    val title: String,
    val description: String,
    val accentColor: Long,
    val titleKannada: String = "",
    val descriptionKannada: String = ""
)

data class VerifiedToy(
    val toy: Toy,
    val artisan: Artisan,
    val workshop: Workshop,
    val story: String,
    val storyKannada: String = ""
)
