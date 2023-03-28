package ru.gortea.petter.ui_kit.dropdown

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

class DropdownItem(
    val text: String,
    val onSelected: () -> Unit,
    val style: @Composable () -> TextStyle = { MaterialTheme.typography.body2 }
)
