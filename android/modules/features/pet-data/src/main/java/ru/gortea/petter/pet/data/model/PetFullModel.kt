package ru.gortea.petter.pet.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import java.time.LocalDate

@Serializable
data class PetFullModel(
    val id: String,
    val ownerId: String,
    val name: String,
    val price: String,
    val species: Species,
    val breed: String,
    val gender: Gender,
    @Contextual val birthDate: LocalDate,
    val height: Int? = null,
    val weight: Int? = null,
    val achievements: Map<String, String> = emptyMap(),
    val vaccinations: List<String> = emptyList(),
    val description: String,
    val imagePaths: List<String>,
    val state: PetCardState
)
