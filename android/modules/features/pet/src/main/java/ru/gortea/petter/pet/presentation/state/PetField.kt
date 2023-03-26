package ru.gortea.petter.pet.presentation.state

import androidx.annotation.StringRes
import ru.gortea.petter.pet.data.model.constants.AchievementLevel
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.text_field.isNumber
import java.time.LocalDate

internal sealed class PetField(
    @StringRes open val titleRes: Int,
    open val fieldName: PetFieldName,
    open val valid: Boolean
) {
    abstract fun validated(): PetField

    data class SimplePetField(
        override val titleRes: Int,
        override val fieldName: PetFieldName,
        val textField: TextFieldState,
        override val valid: Boolean = true,
        val emptyCorrect: Boolean = false
    ) : PetField(titleRes, fieldName, valid) {

        override fun validated(): PetField {
            val isNumInvalid = textField.isNumber() && textField.text.toIntOrNull() == null
            val invalid = if (emptyCorrect) {
                textField.text.isNotEmpty() && isNumInvalid
            } else {
                textField.text.isEmpty() || isNumInvalid
            }

            return copy(textField = textField.copy(isIncorrect = invalid), valid = !invalid)
        }
    }

    data class EnumPetField(
        override val titleRes: Int,
        override val fieldName: PetFieldName,
        val enum: PetEnum,
        val selectedKey: String = enum.values.first().second,
        override val valid: Boolean = true
    ) : PetField(titleRes, fieldName, valid) {

        fun selectedRes(): Int = enum.values.find { it.second == selectedKey }!!.first

        override fun validated(): PetField = this
    }

    data class AchievementPetField(
        override val titleRes: Int,
        val map: Map<TextFieldState, AchievementLevel>,
        override val valid: Boolean = true
    ) : PetField(titleRes, PetFieldName.ACHIEVEMENTS, valid) {

        override fun validated(): PetField {
            var isValid = true

            val validated = map.mapKeys {
                val invalid = it.key.text.isEmpty()
                if (invalid) isValid = false
                it.key.copy(isIncorrect = invalid)
            }

            return copy(map = validated, valid = isValid && map.isNotEmpty())
        }
    }

    data class ListPetField(
        override val titleRes: Int,
        override val fieldName: PetFieldName,
        val list: List<TextFieldState>,
        override val valid: Boolean = true
    ) : PetField(titleRes, fieldName, valid) {

        override fun validated(): PetField {
            var isValid = true

            val validated = list.map {
                val invalid = it.text.isEmpty()
                if (invalid) isValid = false
                it.copy(isIncorrect = invalid)
            }

            return copy(list = validated, valid = isValid && list.isNotEmpty())
        }
    }

    data class DatePetField(
        override val titleRes: Int,
        override val fieldName: PetFieldName,
        val date: LocalDate?,
        override val valid: Boolean = true
    ) : PetField(titleRes, fieldName, valid) {

        override fun validated(): PetField {
            return copy(valid = date != null)
        }
    }
}
