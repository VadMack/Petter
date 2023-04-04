package ru.gortea.petter.ui_kit.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TextWithIcon(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        leadingIcon?.invoke()

        Text(
            text = text,
            style = style,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )

        trailingIcon?.invoke()
    }
}
