package ru.gortea.petter.ui_kit

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.*

@Composable
fun TextField(
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
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

        @OptIn(ExperimentalMaterialApi::class)
        BasicTextField(
            value = text,
            modifier = Modifier
                .background(colors.backgroundColor(enabled).value, shape)
                .fillMaxWidth(),
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = mergedTextStyle,
            cursorBrush = SolidColor(colors.cursorColor(isError).value),
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            singleLine = singleLine,
            maxLines = maxLines,
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.OutlinedTextFieldDecorationBox(
                    value = text,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    placeholder = { Placeholder(text = placeholder) },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    singleLine = singleLine,
                    enabled = enabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = PaddingValues(8.dp),
                    border = {
                        TextFieldDefaults.BorderBox(
                            enabled,
                            isError,
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
    // Todo change to theme colors
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
            var state: String by remember { mutableStateOf("") }
            TextField(
                text = state,
                placeholder = "Input text",
                label = "label",
                onValueChange = { state = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
