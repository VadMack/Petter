package ru.gortea.petter.ui_kit.toolbar

import androidx.compose.runtime.Composable
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.icon.ClickableIcon

@Composable
fun CloseIcon(
    onClick: () -> Unit
) {
    ClickableIcon(
        icon = R.drawable.ic_close,
        onClick = onClick
    )
}
