package ru.gortea.petter.ui_kit.toolbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.PetterAppTheme

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    text: String = "",
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (startIcon != null) {
            startIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h3.copy(
                    color = MaterialTheme.colors.onBackground
                )
            )

            if (endIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                endIcon()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Toolbar_Preview() {
    PetterAppTheme {
        Toolbar(
            text = "Hello Petter"
        )
    }
}
