package ru.gortea.petter.ui_kit.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
    size: Dp = 24.dp,
    tint: Color? = null
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .paint(
                painterResource(icon),
                colorFilter = tint?.let { ColorFilter.tint(tint) }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                enabled = enabled,
                onClick = onClick
            )
    )
}
