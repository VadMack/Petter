package ru.gortea.petter.ui_kit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.PetterAppTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
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
private fun PrimaryButton_Preview() {
    PetterAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            PrimaryButton(
                text = "Button",
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
