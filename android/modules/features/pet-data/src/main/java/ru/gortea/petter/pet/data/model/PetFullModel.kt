package ru.gortea.petter.pet.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.pet.data.model.constants.AchievementLevel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import java.time.LocalDate

@Serializable
data class PetFullModel(
    val id: String,
    val ownerId: String,
    val name: String,
    val price: Int,
    val species: Species,
    val breed: String,
    val gender: Gender,
    @Contextual val birthDate: LocalDate? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val achievements: Map<String, AchievementLevel> = emptyMap(),
    val vaccinations: List<String> = emptyList(),
    val description: String? = null,
    val imagePaths: List<String> = emptyList(),
    val state: PetCardState,
    val liked: Boolean = false
) : Arguments {

    val photoPath: String?
        get() = imagePaths.firstOrNull()?.let { avatar -> "http://10.0.2.2:8080/api/files/${avatar}" }

    val photoPathSegments: List<String>?
        get() = imagePaths.firstOrNull()?.split("/")
}
