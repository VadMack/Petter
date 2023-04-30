package ru.gortea.petter.chat.list.ui

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.chat.list.R
import ru.gortea.petter.chat.list.di.ChatListComponent
import ru.gortea.petter.chat.list.navigation.ChatListNavTarget
import ru.gortea.petter.chat.list.presentation.ChatListUiEvent
import ru.gortea.petter.chat.list.presentation.createChatListStore
import ru.gortea.petter.chat.list.ui.mapper.ChatListUiStateMapper
import ru.gortea.petter.chat.list.ui.state.ChatListUiState
import ru.gortea.petter.chat.list.ui.state.model.MessageUiModel
import ru.gortea.petter.chat.list.ui.state.model.RoomUiModel
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.avatar.Avatar
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.placeholder.EmptyPlaceholder
import ru.gortea.petter.ui_kit.placeholder.ErrorPlaceholder
import ru.gortea.petter.ui_kit.placeholder.LoadingPlaceholder
import ru.gortea.petter.ui_kit.separator.Separator
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun ChatListScreen(
    router: PetterRouter<ChatListNavTarget>
) {
    val component = getComponent<ChatListComponent>()

    val store by storeHolder {
        createChatListStore(router, component)
    }

    store.collect(ChatListUiStateMapper()) { state ->
        ChatListScreen(
            state = state,
            chatClicked = { store.dispatch(ChatListUiEvent.OpenChat(it)) },
            reload = { store.dispatch(ChatListUiEvent.LoadChats) }
        )
    }
}

@Composable
private fun ChatListScreen(
    state: ChatListUiState,
    chatClicked: (String) -> Unit,
    reload: () -> Unit
) {
    Scaffold(
        topBar = { Toolbar(text = stringResource(R.string.chats_title)) }
    ) {
        ChatListScreenContent(
            state = state,
            chatClicked = chatClicked,
            reload = reload,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun ChatListScreenContent(
    state: ChatListUiState,
    chatClicked: (String) -> Unit,
    reload: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.chats) {
        is DataState.Content -> ChatList(
            items = state.chats.content,
            refreshing = state.chats.refreshing,
            chatClicked = chatClicked,
            reload = reload,
            modifier = modifier
        )
        is DataState.Loading, DataState.Empty -> LoadingPlaceholder(
            modifier = modifier.fillMaxSize()
        )
        is DataState.Fail -> ErrorPlaceholder(
            modifier = modifier.fillMaxSize(),
            reloadClicked = reload
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ChatList(
    items: List<RoomUiModel>,
    refreshing: Boolean,
    chatClicked: (String) -> Unit,
    reload: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullRefreshState(refreshing, reload)

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (items.isEmpty()) {
                item {
                    EmptyPlaceholder(
                        text = stringResource(R.string.no_chats_placeholder),
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            } else {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(items = items, key = { it.id }) { item ->
                    ChatRoomItem(
                        item = item,
                        chatClicked = chatClicked,
                        needSeparator = item.id != items.last().id
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ChatRoomItem(
    item: RoomUiModel,
    chatClicked: (String) -> Unit,
    needSeparator: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .clickable { chatClicked(item.companionId) }
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
    ) {
        Avatar(
            image = item.avatarPath?.let {
                rememberAsyncImagePainter(
                    it,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(UiKitR.drawable.ic_person_placeholder),
                    error = painterResource(UiKitR.drawable.ic_person_placeholder)
                )
            },
            size = 50.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            RoomItemDescription(userName = item.companionName, message = item.message)
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(8.dp))
            if (needSeparator) {
                Separator(padding = 0.dp)
            }
        }
    }
}

@Composable
private fun RoomItemDescription(
    userName: String,
    message: MessageUiModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        MessageTitle(userName = userName, dateTime = message.dateTime)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            message.authorNameRes?.let { nameRes ->
                Text(
                    text = stringResource(nameRes),
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
            }

            val text = message.content.ifEmpty { stringResource(R.string.empty_room) }
            Text(
                text = text,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.weight(1f)
            )

            message.iconRes?.let { icon ->
                Icon(
                    icon = icon,
                    tint = message.iconTint
                )
            }
        }
    }
}

@Composable
private fun MessageTitle(
    userName: String,
    dateTime: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = userName,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = dateTime,
            maxLines = 1,
            style = MaterialTheme.typography.caption.copy(
                color = Base600,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListItem_Preview() {
    PetterAppTheme {
        ChatRoomItem(
            item = RoomUiModel(
                id = "",
                companionId = "",
                companionName = "Лучший человек",
                avatarPath = null,
                message = MessageUiModel(
                    content = "Привет",
                    dateTime = "20:00"
                )
            ),
            chatClicked = {},
            needSeparator = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListScreen_Preview() {
    val state = ChatListUiState(
        chats = DataState.Content(
            listOf()
        )
    )

    PetterAppTheme {
        ChatListScreen(
            state = state,
            chatClicked = {},
            reload = {}
        )
    }
}
