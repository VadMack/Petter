package ru.gortea.petter.pet.list.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.data.paging.android.rememberPagingState
import ru.gortea.petter.data.paging.model.PagingDataState.Initial
import ru.gortea.petter.data.paging.model.PagingDataState.Paged
import ru.gortea.petter.navigation.NavCommand
import ru.gortea.petter.pet.list.R
import ru.gortea.petter.pet.list.di.PetListComponent
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.list.navigation.commands.PetListNavCommand
import ru.gortea.petter.pet.list.presentation.PetListUiEvent
import ru.gortea.petter.pet.list.presentation.createPetListStore
import ru.gortea.petter.pet.list.ui.mapper.PetListUiStateMapper
import ru.gortea.petter.pet.list.ui.state.PetListUiState
import ru.gortea.petter.pet.list.ui.state.model.HideState
import ru.gortea.petter.pet.list.ui.state.model.LikeState
import ru.gortea.petter.pet.list.ui.state.model.PetListItem
import ru.gortea.petter.theme.Accent600
import ru.gortea.petter.theme.Base100
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.theme.Male
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.TextModel
import ru.gortea.petter.ui_kit.avatar.Avatar
import ru.gortea.petter.ui_kit.button.TextButton
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.placeholder.LoadingPlaceholder
import ru.gortea.petter.ui_kit.text.TextWithIcon
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
fun PetList(
    listKey: PetListKeyModel,
    command: NavCommand,
    openPetCard: (String) -> Unit
) {
    val component = getComponent<PetListComponent>()
    val formatter = remember { component.dateFormatter }
    val store by storeHolder {
        createPetListStore(component, listKey.pageSize, openPetCard)
    }

    store.collect(PetListUiStateMapper(formatter)) { state ->
        PetList(
            state = state,
            clicked = { store.dispatch(PetListUiEvent.OpenPet(it.id)) },
            likeClicked = { store.dispatch(PetListUiEvent.LikePet(it.id)) },
            unlikeClicked = { store.dispatch(PetListUiEvent.DislikePet(it.id)) },
            reloadPage = { store.dispatch(PetListUiEvent.ReloadPage) },
            loadPage = { store.dispatch(PetListUiEvent.LoadPage) }
        )
    }

    LaunchedEffect(command) {
        when (command) {
            is PetListNavCommand.InvalidateList -> store.dispatch(PetListUiEvent.Invalidate(listKey))
            is PetListNavCommand.PetDisliked -> store.dispatch(PetListUiEvent.DislikePet(command.id))
            is PetListNavCommand.PetLiked -> store.dispatch(PetListUiEvent.LikePet(command.id))
        }
    }

    rememberSaveable(listKey) {
        store.dispatch(PetListUiEvent.Invalidate(listKey))
        true
    }
}

@Composable
private fun PetList(
    state: PetListUiState,
    clicked: (PetListItem) -> Unit,
    likeClicked: (PetListItem) -> Unit,
    unlikeClicked: (PetListItem) -> Unit,
    reloadPage: () -> Unit,
    loadPage: () -> Unit
) {
    when (state.dataState) {
        is Initial.Empty, is Initial.Loading -> LoadingPlaceholder()
        is Initial.Fail -> Unit // Todo add error placeholder
        is Paged -> {
            if (state.dataState.content.isEmpty()) {
                EmptyPlaceholder()
            } else {
                PetListPaged(
                    state = state.dataState,
                    offset = state.offset,
                    clicked = clicked,
                    likeClicked = likeClicked,
                    unlikeClicked = unlikeClicked,
                    reloadPage = reloadPage,
                    loadPage = loadPage
                )
            }
        }
    }
}

@Composable
private fun EmptyPlaceholder() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            icon = UiKitR.drawable.ic_paw_outline,
            tint = Base500,
            size = 90.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.empty_list),
            style = MaterialTheme.typography.h3.copy(Base500),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun PetListPaged(
    state: Paged<PetListItem>,
    offset: Int,
    clicked: (PetListItem) -> Unit,
    likeClicked: (PetListItem) -> Unit,
    unlikeClicked: (PetListItem) -> Unit,
    reloadPage: () -> Unit,
    loadPage: () -> Unit
) {
    LazyColumn(
        state = rememberPagingState(loadPage, state, offset),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
        }

        items(items = state.content, key = { it.id }) { item ->
            PetListItem(
                item = item,
                clicked = clicked,
                likeClicked = likeClicked,
                unlikeClicked = unlikeClicked,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        when (state) {
            is Paged.Content -> item { Spacer(modifier = Modifier.height(16.dp)) }
            is Paged.Loading -> item { PetListLoadingItem() }
            is Paged.Fail -> item { PetListErrorItem(reloadPage) }
        }
    }
}

@Composable
private fun PetListLoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun PetListErrorItem(reloadClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextButton(
            text = stringResource(R.string.reload),
            onClick = reloadClicked,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun PetListItem(
    item: PetListItem,
    clicked: (PetListItem) -> Unit,
    likeClicked: (PetListItem) -> Unit,
    unlikeClicked: (PetListItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.background, RoundedCornerShape(16.dp))
                .clickable { clicked(item) }
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            AvatarWithAward(item.avatar, item.hasAwards)

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                NameRow(
                    item = item,
                    likeClicked = likeClicked,
                    unlikeClicked = unlikeClicked
                )

                ProvideTextStyle(value = MaterialTheme.typography.body2.copy(color = Base600)) {

                    listOfNotNull(
                        TextModel(item.breed) to UiKitR.drawable.ic_paw,
                        TextModel(item.age) to UiKitR.drawable.ic_birthday,
                        item.price to UiKitR.drawable.ic_cash,
                        item.address?.let {
                            if (it.isNotEmpty()) TextModel(it) to UiKitR.drawable.ic_marker
                            else null
                        }
                    ).forEach { (text, icon) ->
                        TextWithIcon(
                            text = text.getText(),
                            verticalAlignment = Alignment.Top,
                            leadingIcon = {
                                Icon(
                                    icon = icon,
                                    tint = Base600,
                                    size = 20.dp
                                )
                            }
                        )
                    }
                }
            }
        }

        if (item.hideState == HideState.HIDDEN) {
            Spacer(
                modifier = Modifier
                    .background(Base100.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun NameRow(
    item: PetListItem,
    likeClicked: (PetListItem) -> Unit,
    unlikeClicked: (PetListItem) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextWithIcon(
            text = item.name,
            style = MaterialTheme.typography.h4,
            verticalAlignment = Alignment.Top,
            trailingIcon = {
                Icon(
                    icon = item.genderIcon,
                    tint = item.genderIconTint,
                    size = 20.dp
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        when {
            item.likeState != LikeState.NOT_AVAILABLE -> Like(
                likeState = item.likeState,
                likeClicked = { likeClicked(item) },
                unlikeClicked = { unlikeClicked(item) }
            )
        }
    }
}

@Composable
private fun Like(
    likeState: LikeState,
    likeClicked: () -> Unit,
    unlikeClicked: () -> Unit
) {
    when (likeState) {
        LikeState.LIKED -> ClickableIcon(
            icon = UiKitR.drawable.ic_liked,
            onClick = unlikeClicked,
            size = 22.dp
        )
        LikeState.UNLIKED -> ClickableIcon(
            icon = UiKitR.drawable.ic_unliked,
            tint = Base600,
            onClick = likeClicked,
            size = 22.dp
        )
        LikeState.NOT_AVAILABLE -> Unit
    }
}

@Composable
private fun AvatarWithAward(avatar: Uri?, hasAwards: Boolean) {
    Box {
        Avatar(
            image = avatar?.let {
                rememberAsyncImagePainter(
                    it,
                    placeholder = painterResource(UiKitR.drawable.ic_pet_placeholder),
                    error = painterResource(UiKitR.drawable.ic_pet_placeholder)
                )
            },
            placeholder = painterResource(UiKitR.drawable.ic_pet_placeholder),
            size = 126.dp,
            shape = RoundedCornerShape(16.dp)
        )

        if (hasAwards) {
            Icon(
                icon = UiKitR.drawable.ic_award,
                tint = Accent600,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x00000000)
@Composable
private fun PetListItem_Preview() {
    PetterAppTheme {
        PetListItem(
            item = PetListItem(
                id = "",
                avatar = null,
                hasAwards = true,
                name = "Нелли",
                genderIcon = UiKitR.drawable.ic_male,
                genderIconTint = Male,
                breed = "Бигль",
                age = "20.05.2001",
                price = TextModel("По договоренности"),
                likeState = LikeState.LIKED,
                hideState = HideState.NOT_AVAILABLE,
                address = null
            ),
            clicked = {},
            likeClicked = {},
            unlikeClicked = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PetList_Preview() {
    PetterAppTheme {
        val items = listOf(
            PetListItem(
                id = "0",
                avatar = null,
                hasAwards = true,
                name = "Нелли",
                genderIcon = UiKitR.drawable.ic_male,
                genderIconTint = Male,
                breed = "Бигль",
                age = "20.05.2001",
                price = TextModel("По договоренности"),
                likeState = LikeState.LIKED,
                hideState = HideState.NOT_AVAILABLE,
                address = ""
            ),
            PetListItem(
                id = "1",
                avatar = null,
                hasAwards = true,
                name = "Нелли",
                genderIcon = UiKitR.drawable.ic_male,
                genderIconTint = Male,
                breed = "Бигль",
                age = "20.05.2001",
                price = TextModel("По договоренности"),
                likeState = LikeState.LIKED,
                hideState = HideState.HIDDEN,
                address = null
            )
        )

        val state = PetListUiState(dataState = Paged.Content(items), offset = 1)

        PetList(
            state = state,
            clicked = {},
            likeClicked = {},
            unlikeClicked = {},
            reloadPage = {},
            loadPage = {}
        )
    }
}
