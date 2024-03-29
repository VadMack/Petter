package ru.gortea.petter.pet.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.gortea.petter.data.model.Arguments
import ru.gortea.petter.network.url.BASE_URL
import ru.gortea.petter.pet.data.model.constants.AchievementLevel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.profile.data.remote.model.AddressModel
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
    val achievements: Map<String, AchievementLevel>? = null,
    val vaccinations: List<String>? = null,
    val description: String? = null,
    val imagePaths: List<String> = emptyList(),
    val state: PetCardState? = PetCardState.OPEN,
    val liked: Boolean = false,
    val address: AddressModel? = null
) : Arguments {

    val photoPath: String?
        get() = imagePaths.firstOrNull()?.let { avatar -> "http://${BASE_URL}/api/files/${avatar}" }

    val photoPathSegments: List<String>?
        get() = imagePaths.firstOrNull()?.split("/")
}
