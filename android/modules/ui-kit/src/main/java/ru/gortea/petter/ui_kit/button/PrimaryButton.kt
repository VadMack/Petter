package ru.gortea.petter.ui_kit.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
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
    isClickable: Boolean = true,
    state: ButtonState = ButtonState(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = { if (isClickable) onClick() },
        modifier = modifier.height(38.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 4.dp,
            focusedElevation = 4.dp,
        )
    ) {
        leadingIcon?.invoke()

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = MaterialTheme.colors.onPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.button
            )
        }

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
                    .fillMaxWidth(),
                state = ButtonState(true)
            )
        }
    }
}
