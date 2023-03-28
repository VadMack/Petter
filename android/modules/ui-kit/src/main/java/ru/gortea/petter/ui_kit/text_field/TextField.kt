package ru.gortea.petter.ui_kit.text_field

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.theme.Base700
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.body3

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun TextField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(4.dp)),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colors.onBackground,
        backgroundColor = MaterialTheme.colors.background,
        errorCursorColor = MaterialTheme.colors.error,
        unfocusedIndicatorColor = Base500,
        focusedIndicatorColor = MaterialTheme.colors.primary
    )
) {
    val textColor = MaterialTheme.typography.body1.color.takeOrElse {
        colors.textColor(enabled).value
    }

    val mergedTextStyle = MaterialTheme.typography.body1.merge(TextStyle(color = textColor))
    Column(
        modifier = modifier
    ) {
        label?.let {
            Label(
                text = label,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        BasicTextField(
            value = state.textRes?.let { stringResource(it) } ?: state.text,
            modifier = Modifier
                .background(colors.backgroundColor(enabled).value, shape)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = state.keyboardType),
            keyboardActions = KeyboardActions { keyboardController?.hide() },
            onValueChange = { onValueChange(it) },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.cursorColor(state.isIncorrect).value),
            visualTransformation = state.visualTransformation,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = state.text,
                    visualTransformation = state.visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = { Placeholder(text = placeholder) },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = state.isIncorrect,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = PaddingValues(8.dp),
                    border = {
                        TextFieldDefaults.BorderBox(
                            enabled,
                            state.isIncorrect,
                            interactionSource,
                            colors,
                            shape
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun Placeholder(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body3.copy(color = Base600)
    )
}

@Composable
private fun Label(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1.copy(color = Base700),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun TextField_Preview() {
    PetterAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            var state by remember { mutableStateOf(TextFieldState()) }
            TextField(
                state = state,
                placeholder = "Input text",
                label = "label",
                onValueChange = { state = state.copy(text = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
