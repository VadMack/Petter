package ru.gortea.petter.ui_kit.dialogs.bottom_sheet

import androidx.compose.ui.text.TextStyle

data class BottomSheetItem(
    val text: String,
    val style: TextStyle,
    val onClick: () -> Unit
)
