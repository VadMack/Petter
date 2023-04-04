package ru.gortea.petter.ui_kit.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.button.SecondaryTextButton

@Composable
fun ErrorPlaceholder(
    reloadClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.loading_error),
            style = MaterialTheme.typography.body1.copy(Base500)
        )

        SecondaryTextButton(
            text = stringResource(R.string.reload),
            onClick = reloadClicked
        )
    }
}
