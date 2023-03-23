package ru.gortea.petter.auth.navigation.nodes.registration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.auth.registration.fill_account.ui.FillAccountScreen

class FillAccountNode(
    buildContext: BuildContext
) : Node(buildContext = buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        FillAccountScreen { finish() }
    }
}

