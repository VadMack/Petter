package ru.gortea.petter.ui_kit.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.R


@Composable
fun EditAvatar(
    image: Painter?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.ic_person_placeholder),
    shape: Shape = CircleShape,
    avatarSize: Dp = 116.dp
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier.size(width = avatarSize, height = avatarSize + 12.dp)
    ) {
        Avatar(
            image = image,
            shape = shape,
            placeholder = placeholder,
            size = avatarSize,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .clip(shape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = onClick
                )
        )

        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = stringResource(R.string.avatar),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary, BlendMode.Lighten),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditAvatar_Preview() {
    PetterAppTheme {
        EditAvatar(
            image = painterResource(R.drawable.ic_edit),
            onClick = {}
        )
    }
}
