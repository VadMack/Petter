package ru.gortea.petter.ui_kit.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Icon(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color? = null,
    size: Dp = 24.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .paint(
                painterResource(icon),
                colorFilter = tint?.let { ColorFilter.tint(tint) }
            )
    )
}
