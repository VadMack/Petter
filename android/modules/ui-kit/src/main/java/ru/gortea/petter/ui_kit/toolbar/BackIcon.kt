package ru.gortea.petter.ui_kit.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.gortea.petter.ui_kit.R

@Composable
fun BackIcon(
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier.clickable(onClick = onClick),
        painter = painterResource(R.drawable.ic_back),
        contentDescription = stringResource(R.string.back),
        tint = MaterialTheme.colors.onBackground
    )
}
