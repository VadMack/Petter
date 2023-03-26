package ru.gortea.petter.pet.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.pet.ui.PetEditScreen

class PetEditNode(
    buildContext: BuildContext,
    private val petId: String?
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        PetEditScreen(petId)
    }
}
