package ru.gortea.petter.ui_kit.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.icon.Icon

@Composable
fun Fab(
    text: String,
    @DrawableRes leadingIcon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = { onClick() },
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 10.dp,
            end = 12.dp,
            top = 8.dp,
            bottom = 8.dp
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            disabledElevation = 8.dp,
            hoveredElevation = 4.dp,
            focusedElevation = 4.dp,
        ),
        colors = ButtonDefaults.buttonColors(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                icon = leadingIcon,
                tint = MaterialTheme.colors.onPrimary,
                size = 20.dp
            )

            Text(
                text = text,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Fab_Preview() {
    PetterAppTheme {
        Fab(
            text = "Добавить",
            leadingIcon = R.drawable.ic_plus,
            onClick = {}
        )
    }
}
