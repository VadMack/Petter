package ru.gortea.petter.ui_kit.toolbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.gortea.petter.ui_kit.R

@Composable
fun CloseIcon(
    onClick: () -> Unit
) {
    ClickableIcon(
        icon = R.drawable.ic_close,
        size = 32.dp,
        onClick = onClick
    )
}
