package ru.gortea.petter.pet.navigation.nodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.ViewModelNode
import ru.gortea.petter.pet.navigation.PetNavTarget
import ru.gortea.petter.pet.ui.PetEditScreen

internal class PetEditNode(
    buildContext: BuildContext,
    private val router: PetterRouter<PetNavTarget>,
    private val petId: String?
) : ViewModelNode(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        PetEditScreen(petId, router)
    }
}
