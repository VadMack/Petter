package ru.gortea.petter.home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.home.R
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.home.presentation.HomeState
import ru.gortea.petter.home.presentation.HomeUiEvent
import ru.gortea.petter.home.presentation.createHomeStore
import ru.gortea.petter.navigation.NavCommand
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.list.navigation.commands.PetListNavCommand
import ru.gortea.petter.pet.list.ui.PetList
import ru.gortea.petter.pet.navigation.commands.PetNavCommand
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.toolbar.Toolbar

@Composable
internal fun HomeScreen(router: PetterRouter<HomeNavTarget>) {
    val command by router.commands.collectAsState()
    val store by storeHolder { createHomeStore(router) }

    store.collect { state ->
        HomeScreen(
            state = state,
            openPet = { store.dispatch(HomeUiEvent.OpenPet(it)) },
            command = command
        )
    }
}

@Composable
private fun HomeScreen(
    state: HomeState,
    openPet: (String) -> Unit,
    command: NavCommand = NavCommand.Empty
) {
    Scaffold(
        topBar = {
            Toolbar(text = stringResource(R.string.pets))
        }
    ) {
        HomeScreenContent(
            state = state,
            command = command,
            openPet = openPet,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    command: NavCommand,
    openPet: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PetList(
        listKey = state.listKeyModel,
        openPetCard = openPet,
        command = command.toPetListNavCommand(),
        pullToRefreshEnabled = true,
        modifier = modifier
    )
}

private fun NavCommand.toPetListNavCommand(): NavCommand {
    return when (this) {
        is PetNavCommand.PetLikedChanged -> if (liked) {
            PetListNavCommand.PetLiked(id)
        } else {
            PetListNavCommand.PetDisliked(id)
        }
        else -> NavCommand.Empty
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreen_Preview() {
    PetterAppTheme {
        HomeScreen(
            state = HomeState(),
            openPet = {}
        )
    }
}
