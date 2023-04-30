package ru.gortea.petter.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.chat.R
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.chat.di.ChatComponent
import ru.gortea.petter.chat.navigation.ChatNavTarget
import ru.gortea.petter.chat.presentation.ChatCreateRoomStore
import ru.gortea.petter.chat.presentation.ChatRoomCreationEvent
import ru.gortea.petter.chat.presentation.ChatStore
import ru.gortea.petter.chat.presentation.ChatUiEvent
import ru.gortea.petter.chat.ui.mapper.ChatCreateRoomUiStateMapper
import ru.gortea.petter.chat.ui.mapper.ChatUiStateMapper
import ru.gortea.petter.chat.ui.model.ChatUiState
import ru.gortea.petter.chat.ui.model.MessageGravity
import ru.gortea.petter.chat.ui.model.MessageUiModel
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.paging.android.rememberPagingState
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.theme.Base100
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.Primary100
import ru.gortea.petter.theme.Primary600
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.placeholder.EmptyPlaceholder
import ru.gortea.petter.ui_kit.placeholder.ErrorPlaceholder
import ru.gortea.petter.ui_kit.placeholder.LoadingPlaceholder
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.toolbar.AvatarToolbar
import ru.gortea.petter.ui_kit.toolbar.BackIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun ChatScreen(
    userId: String,
    router: PetterRouter<ChatNavTarget>
) {
    val component = getComponent<ChatComponent>()

    val store by storeHolder("ChatScreen$userId") {
        ChatCreateRoomStore(userId, router, component)
    }

    store.collect(ChatCreateRoomUiStateMapper()) { state ->
        ChatCreateRoomScreen(
            state = state,
            router = router,
            reloadClicked = { store.dispatch(ChatRoomCreationEvent.CreateChatRoom(userId)) },
            backClicked = { store.dispatch(ChatUiEvent.Back) }
        )
    }
}

@Composable
private fun ChatCreateRoomScreen(
    state: ChatUiState.NoRoomUiState,
    router: PetterRouter<ChatNavTarget>,
    reloadClicked: () -> Unit,
    backClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            if (state.room !is DataState.Content) {
                Toolbar(startIcon = { BackIcon(backClicked) })
            }
        }
    ) {
        ChatCreateRoomScreen(
            state = state,
            router = router,
            reloadClicked = reloadClicked,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun ChatCreateRoomScreen(
    state: ChatUiState.NoRoomUiState,
    router: PetterRouter<ChatNavTarget>,
    reloadClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.room) {
        is DataState.Loading, DataState.Empty -> LoadingPlaceholder(
            modifier = modifier.fillMaxSize()
        )
        is DataState.Fail -> ErrorPlaceholder(
            reloadClicked = reloadClicked,
            modifier = modifier.fillMaxSize()
        )
        is DataState.Content -> ChatCreateRoomScreenContent(
            room = state.room.content,
            router = router
        )
    }
}

@Composable
private fun ChatCreateRoomScreenContent(
    room: ChatRoomModel,
    router: PetterRouter<ChatNavTarget>
) {
    val component = getComponent<ChatComponent>()

    val store by storeHolder("ChatCreateRoomScreenContent${room.id}") {
        ChatStore(
            room = room,
            component = component,
            router = router
        )
    }

    store.collect(ChatUiStateMapper()) { state ->
        ChatScreen(
            state = state,
            backClicked = { store.dispatch(ChatUiEvent.Back) },
            openCompanionProfile = { store.dispatch(ChatUiEvent.OpenCompanionProfile) },
            loadPage = { store.dispatch(ChatUiEvent.LoadPage) },
            messageFieldChanged = { store.dispatch(ChatUiEvent.MessageFieldChanged(it)) },
            sendMessageClicked = { store.dispatch(ChatUiEvent.SendMessage) }
        )
    }
}

@Composable
private fun ChatScreen(
    state: ChatUiState.ContentUiState,
    backClicked: () -> Unit,
    openCompanionProfile: () -> Unit,
    loadPage: () -> Unit,
    messageFieldChanged: (String) -> Unit,
    sendMessageClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            AvatarToolbar(
                text = state.companionName,
                avatar = state.companionAvatar?.let {
                    rememberAsyncImagePainter(
                        model = it,
                        contentScale = ContentScale.Crop,
                        error = painterResource(UiKitR.drawable.ic_person_placeholder),
                        placeholder = painterResource(UiKitR.drawable.ic_person_placeholder)
                    )
                },
                profileClicked = openCompanionProfile,
                startIcon = { BackIcon(backClicked) }
            )
        }
    ) {
        ChatScreen(
            state = state,
            loadPage = loadPage,
            messageFieldChanged = messageFieldChanged,
            sendMessageClicked = sendMessageClicked,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun ChatScreen(
    state: ChatUiState.ContentUiState,
    loadPage: () -> Unit,
    messageFieldChanged: (String) -> Unit,
    sendMessageClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        when (state.messages) {
            is PagingDataState.Initial.Fail -> ErrorPlaceholder(modifier = Modifier.fillMaxSize())
            is PagingDataState.Initial -> LoadingPlaceholder(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            is PagingDataState.Paged -> ChatScreenContent(
                state = state.messages,
                offset = state.offset,
                loadPage = loadPage,
                modifier = Modifier.weight(1f)
            )
        }

        if (state.messages !is PagingDataState.Initial.Fail) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    state = state.messageField,
                    placeholder = stringResource(R.string.message_placeholder),
                    onValueChange = messageFieldChanged,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(max = 84.dp)
                )

                val canSend = state.messageField.text.getStringText().isNotBlank()
                ClickableIcon(
                    icon = UiKitR.drawable.ic_send,
                    onClick = sendMessageClicked,
                    enabled = canSend,
                    tint = if (canSend) MaterialTheme.colors.primary else Base600
                )
            }
        }
    }
}

@Composable
private fun ChatScreenContent(
    state: PagingDataState.Paged<MessageUiModel>,
    offset: Int,
    loadPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberPagingState(loadPage, state, offset)

    LazyColumn(
        reverseLayout = true,
        state = listState,
        modifier = modifier
    ) {
        if (state.content.isEmpty()) {
            item {
                EmptyPlaceholder(
                    text = stringResource(R.string.no_messages_placeholder),
                    modifier = Modifier.fillParentMaxSize()
                )
            }
        } else {
            items(state.content, key = { it.id }) { item ->
                MessageItem(item = item)
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    LaunchedEffect(key1 = state.content.size) {
        if (!listState.isScrollInProgress && listState.firstVisibleItemIndex < 3) {
            listState.animateScrollToItem(0)
        }
    }
}

@Composable
private fun MessageItem(item: MessageUiModel) {
    when (item.gravity) {
        MessageGravity.START -> Message(
            item = item,
            arrangement = Arrangement.Start,
            alignment = Alignment.Start,
            background = Base100,
            dateColor = Base600
        )
        MessageGravity.END -> Message(
            item = item,
            arrangement = Arrangement.End,
            alignment = Alignment.End,
            background = Primary100,
            dateColor = Primary600
        )
    }
}

@Composable
private fun Message(
    item: MessageUiModel,
    arrangement: Arrangement.Horizontal,
    alignment: Alignment.Horizontal,
    background: Color,
    dateColor: Color
) {
    Row(
        horizontalArrangement = arrangement,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = alignment,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.iconRes != null) {
                    Icon(
                        icon = item.iconRes,
                        tint = item.iconTint,
                        size = 16.dp
                    )
                }

                Box(
                    modifier = Modifier
                        .background(background, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = item.content,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }

            Text(
                text = item.dateTime,
                style = MaterialTheme.typography.caption.copy(color = dateColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreen_Preview() {
    val state = ChatUiState.ContentUiState(
        companionName = "Makar",
        companionAvatar = null,
        messages = PagingDataState.Paged.Content(
            listOf(
                MessageUiModel(
                    id = "",
                    gravity = MessageGravity.START,
                    content = "Message",
                    dateTime = "12:40",
                    iconRes = null,
                    iconTint = null
                )
            )
        ),
        messageField = TextFieldState(),
        offset = 30
    )

    PetterAppTheme {
        ChatScreen(
            state = state,
            backClicked = {},
            openCompanionProfile = {},
            loadPage = {},
            messageFieldChanged = {},
            sendMessageClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageItem_Preview() {
    val message = MessageUiModel(
        id = "",
        gravity = MessageGravity.END,
        content = "Message",
        dateTime = "12:40",
        iconRes = null,
        iconTint = null
    )

    PetterAppTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            MessageItem(item = message)
        }
    }
}
