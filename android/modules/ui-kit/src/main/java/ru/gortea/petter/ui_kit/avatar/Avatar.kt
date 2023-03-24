package ru.gortea.petter.ui_kit.avatar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.gortea.petter.ui_kit.R

@Composable
fun Avatar(
    image: Painter?,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.ic_person_placeholder),
    size: Dp = 116.dp,
    shape: Shape = CircleShape
) {
    Surface(
        shape = shape,
        modifier = modifier.size(size)
    ) {
        Image(
            painter = image ?: placeholder,
            contentDescription = stringResource(R.string.avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
