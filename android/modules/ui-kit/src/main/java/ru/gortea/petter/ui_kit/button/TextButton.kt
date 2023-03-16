package ru.gortea.petter.ui_kit.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.PetterAppTheme

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        leadingIcon?.invoke()

        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.button
        )

        trailingIcon?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
private fun TextButton_Preview() {
    PetterAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            TextButton(
                text = "Button",
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
