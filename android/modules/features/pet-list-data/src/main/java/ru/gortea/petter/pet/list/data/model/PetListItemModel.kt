package ru.gortea.petter.pet.list.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import java.time.LocalDate

@Serializable
data class PetListItemModel(
    val id: String,
    val ownerId: String,
    val name: String,
    val species: Species,
    val price: Int,
    val breed: String,
    val gender: Gender,
    @Contextual val birthDate: LocalDate,
    val hasAchievements: Boolean,
    val imagePaths: List<String>?,
    val state: PetCardState,
    val liked: Boolean
) {
    val photoPath: String?
        get() = imagePaths?.firstOrNull()?.let { avatar -> "http://10.0.2.2:8080/api/files/${avatar}" }
}
