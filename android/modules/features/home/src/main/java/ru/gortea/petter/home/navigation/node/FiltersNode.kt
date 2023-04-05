package ru.gortea.petter.home.navigation.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import ru.gortea.petter.home.filters.ui.FiltersScreen
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.navigation.node.ViewModelNode
import ru.gortea.petter.pet.list.model.PetListKeyModel

internal class FiltersNode(
    buildContext: BuildContext,
    private val keyModel: PetListKeyModel,
    private val router: PetterRouter<*>
) : ViewModelNode(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        FiltersScreen(router, keyModel)
    }
}
