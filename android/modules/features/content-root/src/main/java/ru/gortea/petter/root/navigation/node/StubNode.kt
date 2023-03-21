package ru.gortea.petter.root.navigation.node

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

internal class StubNode(
    buildContext: BuildContext,
    private val num: Int
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                text = "Stub Screen $num",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
