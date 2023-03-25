package ru.gortea.petter.pet.presentation.state

import android.net.Uri
import ru.gortea.petter.pet.R
import ru.gortea.petter.pet.data.model.PetFullModel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.data.model.constants.PetCardState
import ru.gortea.petter.pet.data.model.constants.Species
import ru.gortea.petter.ui_kit.text_field.TextFieldState

internal data class PetPresentationModel(
    val photoPath: Uri?,
    val fields: List<PetField>,
    val model: PetFullModel?
)

internal fun PetPresentationModel.editMode(editMode: Boolean): PetPresentationModel {
    return model?.toPetPresentationModel(editMode) ?: if (editMode) getDefaultPresentationModel() else error("Cannot edit new pet")
}

internal fun PetPresentationModel.updateField(field: PetField): PetPresentationModel {
    val newFields = fields.map { if (it.fieldName == field.fieldName) field else it }
    return copy(fields = newFields)
}

internal fun getDefaultPresentationModel(): PetPresentationModel {
    val fields = listOf(
        PetField.SimplePetField(R.string.name, PetFieldName.NAME, TextFieldState()),
        PetField.EnumPetField(
            R.string.gender,
            PetFieldName.GENDER,
            PetEnum(Gender.values().map(Gender::toPair))
        ),
        PetField.EnumPetField(
            R.string.species,
            PetFieldName.SPECIES,
            PetEnum(Species.values().map(Species::toPair))
        ),
        PetField.SimplePetField(R.string.breed, PetFieldName.BREED, TextFieldState()),
        PetField.DatePetField(R.string.birth_date, PetFieldName.BIRTH_DATE, null),
        PetField.SimplePetField(R.string.price, PetFieldName.PRICE, TextFieldState())
    )

    return PetPresentationModel(null, fields, null)
}

internal fun PetFullModel.toPetPresentationModel(editMode: Boolean): PetPresentationModel {
    val fields = mutableListOf<PetField>()

    if (editMode) {
        fields.addAll(
            listOf(
                PetField.SimplePetField(R.string.name, PetFieldName.NAME, TextFieldState(name)),
                PetField.EnumPetField(
                    R.string.gender,
                    PetFieldName.GENDER,
                    PetEnum(Gender.values().map(Gender::toPair))
                ),
                PetField.EnumPetField(
                    R.string.species,
                    PetFieldName.SPECIES,
                    PetEnum(Species.values().map(Species::toPair))
                )
            )
        )
    }

    fields.addAll(
        listOf(
            PetField.SimplePetField(R.string.breed, PetFieldName.BREED, TextFieldState(breed)),
            PetField.DatePetField(R.string.birth_date, PetFieldName.BIRTH_DATE, birthDate),
            PetField.SimplePetField(R.string.price, PetFieldName.PRICE, TextFieldState(price))
        )
    )

    achievements?.let { achievements ->
        fields.add(
            PetField.AchievementPetField(
                R.string.weight,
                achievements.mapKeys { TextFieldState(it.key) })
        )
    }

    description?.let { description ->
        fields.add(
            PetField.SimplePetField(
                R.string.description,
                PetFieldName.DESCRIPTION,
                TextFieldState(description)
            )
        )
    }

    weight?.let { weight ->
        fields.add(
            PetField.SimplePetField(
                R.string.weight,
                PetFieldName.WEIGHT,
                TextFieldState(weight.toString())
            )
        )
    }

    height?.let { height ->
        fields.add(
            PetField.SimplePetField(
                R.string.height,
                PetFieldName.HEIGHT,
                TextFieldState(height.toString())
            )
        )
    }

    vaccinations?.let { vaccinations ->
        fields.add(
            PetField.ListPetField(
                R.string.vaccinations,
                PetFieldName.VACCINATIONS,
                vaccinations.map { TextFieldState(it) }
            )
        )
    }

    return PetPresentationModel(photoPath?.let(Uri::parse), fields, this)
}

private fun Gender.toPair(): Pair<Int, String> {
    return when (this) {
        Gender.MALE -> R.string.gender_male to name
        Gender.FEMALE -> R.string.gender_female to name
    }
}

private fun Species.toPair(): Pair<Int, String> {
    return when (this) {
        Species.DOG -> R.string.species_dog to name
        Species.CAT -> R.string.species_cat to name
    }
}

internal fun PetPresentationModel.toPetFullModel(): PetFullModel {
    var fullModel = model ?: PetFullModel(
        id = "",
        ownerId = "",
        name = "",
        price = "",
        species = Species.CAT,
        breed = "",
        gender = Gender.MALE, state = PetCardState.OPEN
    )

    fields.forEach { field ->
        fullModel = when (field) {
            is PetField.SimplePetField -> fullModel.updateField(field)
            is PetField.AchievementPetField -> fullModel.updateField(field)
            is PetField.DatePetField -> fullModel.updateField(field)
            is PetField.EnumPetField -> fullModel.updateField(field)
            is PetField.ListPetField -> fullModel.updateField(field)
        }
    }

    return fullModel
}

private fun PetFullModel.updateField(field: PetField.SimplePetField): PetFullModel {
    val text = field.textField.text
    return when (field.fieldName) {
        PetFieldName.NAME -> copy(name = text)
        PetFieldName.PRICE -> copy(price = text)
        PetFieldName.BREED -> copy(breed = text)
        PetFieldName.HEIGHT -> copy(height = text.toInt())
        PetFieldName.WEIGHT -> copy(weight = text.toInt())
        PetFieldName.DESCRIPTION -> copy(description = text)
        else -> this
    }
}

private fun PetFullModel.updateField(field: PetField.AchievementPetField): PetFullModel {
    if (field.map.isEmpty()) return this

    return copy(
        achievements = field.map.mapKeys { it.key.text }
    )
}

private fun PetFullModel.updateField(field: PetField.DatePetField): PetFullModel {
    return copy(birthDate = field.date)
}

private fun PetFullModel.updateField(field: PetField.EnumPetField): PetFullModel {
    val text = field.selected
    return when (field.fieldName) {
        PetFieldName.SPECIES -> copy(species = Species.valueOf(text))
        PetFieldName.GENDER -> copy(gender = Gender.valueOf(text))
        else -> this
    }
}

private fun PetFullModel.updateField(field: PetField.ListPetField): PetFullModel {
    return copy(vaccinations = field.list.map { it.text })
}
