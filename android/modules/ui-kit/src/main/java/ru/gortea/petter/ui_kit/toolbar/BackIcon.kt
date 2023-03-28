package ru.gortea.petter.ui_kit.toolbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.icon.ClickableIcon

@Composable
fun BackIcon(
    onClick: () -> Unit
) {
    ClickableIcon(
        icon = R.drawable.ic_back,
        size = 24.dp,
        onClick = onClick
    )
}
