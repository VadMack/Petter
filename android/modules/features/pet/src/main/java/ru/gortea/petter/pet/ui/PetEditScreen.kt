package ru.gortea.petter.pet.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.formatters.DateFormatter
import ru.gortea.petter.formatters.SimpleDateFormatter
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.R
import ru.gortea.petter.pet.data.model.constants.AchievementLevel
import ru.gortea.petter.pet.data.model.constants.Gender
import ru.gortea.petter.pet.di.PetComponent
import ru.gortea.petter.pet.navigation.PetNavTarget
import ru.gortea.petter.pet.presentation.PetStore
import ru.gortea.petter.pet.presentation.PetUiEvent
import ru.gortea.petter.pet.presentation.createPetStore
import ru.gortea.petter.pet.presentation.state.PetEnum
import ru.gortea.petter.pet.presentation.state.PetField
import ru.gortea.petter.pet.presentation.state.PetFieldName
import ru.gortea.petter.pet.ui.mapper.PetEditUiStateMapper
import ru.gortea.petter.pet.ui.mapper.iconTint
import ru.gortea.petter.pet.ui.state.edit.PetEditFullUiModel
import ru.gortea.petter.pet.ui.state.edit.PetEditUiState
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.TextModel
import ru.gortea.petter.ui_kit.avatar.EditAvatar
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.SecondaryButton
import ru.gortea.petter.ui_kit.containers.PetDescriptionContainer
import ru.gortea.petter.ui_kit.dialogs.bottom_sheet.BottomSheetDialog
import ru.gortea.petter.ui_kit.dialogs.bottom_sheet.BottomSheetItem
import ru.gortea.petter.ui_kit.dialogs.calendar.CalendarDialog
import ru.gortea.petter.ui_kit.dialogs.selector.MultiSelectionDialog
import ru.gortea.petter.ui_kit.dialogs.selector.SelectorDialog
import ru.gortea.petter.ui_kit.dropdown.Dropdown
import ru.gortea.petter.ui_kit.dropdown.DropdownItem
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.placeholder.ErrorPlaceholder
import ru.gortea.petter.ui_kit.placeholder.LoadingPlaceholder
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.toolbar.CloseIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import java.time.LocalDate
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun PetEditScreen(
    id: String?,
    router: PetterRouter<PetNavTarget>
) {
    var launcherStore: PetStore? = null
    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) { avatar ->
        avatar?.let { launcherStore?.dispatch(PetUiEvent.AvatarChanged(it)) }
    }
    var showModal by remember { mutableStateOf(false) }

    val component = getComponent<PetComponent>()
    val dateFormatter = remember { component.dateFormatter }

    val store by storeHolder {
        createPetStore(
            petId = id,
            editMode = true,
            component = component,
            router = router,
            showModalImageChooser = { showModal = true },
            showImagePicker = { launcher.launch(PickVisualMediaRequest(ImageOnly)) }
        )
    }
    launcherStore = store

    store.collect(PetEditUiStateMapper()) { state ->
        PetEditScreen(
            state = state,
            dateFormatter = dateFormatter,
            backClicked = { store.dispatch(PetUiEvent.GoBack) },
            saveClicked = { store.dispatch(PetUiEvent.UpdatePet) },
            showClicked = { store.dispatch(PetUiEvent.ShowPet) },
            hideClicked = { store.dispatch(PetUiEvent.HidePet) },
            avatarClicked = { store.dispatch(PetUiEvent.AvatarClicked) },
            reloadClicked = { store.dispatch(PetUiEvent.LoadPet(id)) },
            fieldUpdated = { store.dispatch(PetUiEvent.EditField(it)) },
            fieldDeleted = { store.dispatch(PetUiEvent.DeleteField(it)) },
            fieldsAdded = { store.dispatch(PetUiEvent.AddFields(it)) }
        )
    }

    if (showModal) {
        BottomSheetDialog(
            items = listOf(
                BottomSheetItem(
                    text = stringResource(R.string.edit_avatar),
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary),
                    onClick = { store.dispatch(PetUiEvent.AvatarEditClicked) }
                ),
                BottomSheetItem(
                    text = stringResource(R.string.delete_avatar),
                    style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error),
                    onClick = { store.dispatch(PetUiEvent.AvatarDeleteClicked) }
                )
            ),
            onDismiss = { showModal = false }
        )
    }
}

@Composable
internal fun PetEditScreen(
    state: PetEditUiState,
    dateFormatter: DateFormatter,
    backClicked: () -> Unit,
    saveClicked: () -> Unit,
    showClicked: () -> Unit,
    hideClicked: () -> Unit,
    avatarClicked: () -> Unit,
    reloadClicked: () -> Unit,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit,
    fieldsAdded: (List<PetField>) -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                startIcon = { CloseIcon(backClicked) },
                text = state.titleRes?.let { stringResource(it) } ?: ""
            )
        }
    ) {
        PetEditScreenRoot(
            state = state,
            dateFormatter = dateFormatter,
            saveClicked = saveClicked,
            showClicked = showClicked,
            hideClicked = hideClicked,
            avatarClicked = avatarClicked,
            reloadClicked = reloadClicked,
            fieldUpdated = fieldUpdated,
            fieldDeleted = fieldDeleted,
            fieldsAdded = fieldsAdded,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun PetEditScreenRoot(
    state: PetEditUiState,
    dateFormatter: DateFormatter,
    saveClicked: () -> Unit,
    showClicked: () -> Unit,
    hideClicked: () -> Unit,
    avatarClicked: () -> Unit,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit,
    fieldsAdded: (List<PetField>) -> Unit,
    reloadClicked: () -> Unit,
    modifier: Modifier
) {
    when (state.modelStatus) {
        is DataState.Empty -> Unit
        is DataState.Loading -> LoadingPlaceholder(modifier.fillMaxSize())
        is DataState.Content -> {
            PetEditScreenContent(
                state = state.modelStatus.content,
                dateFormatter = dateFormatter,
                saveClicked = saveClicked,
                showClicked = showClicked,
                hideClicked = hideClicked,
                avatarClicked = avatarClicked,
                fieldUpdated = fieldUpdated,
                fieldDeleted = fieldDeleted,
                fieldsAdded = fieldsAdded,
                modifier = modifier
            )
        }
        is DataState.Fail -> ErrorPlaceholder(reloadClicked = reloadClicked)
    }
}

@Composable
private fun PetEditScreenContent(
    state: PetEditFullUiModel,
    dateFormatter: DateFormatter,
    saveClicked: () -> Unit,
    showClicked: () -> Unit,
    hideClicked: () -> Unit,
    avatarClicked: () -> Unit,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit,
    fieldsAdded: (List<PetField>) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditAvatar(
            image = state.photo?.let {
                rememberAsyncImagePainter(
                    it,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(UiKitR.drawable.ic_pet_placeholder),
                    error = painterResource(UiKitR.drawable.ic_pet_placeholder)
                )
            },
            placeholder = painterResource(UiKitR.drawable.ic_pet_placeholder),
            avatarSize = 158.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(bottom = 24.dp, top = 16.dp),
            onClick = avatarClicked
        )

        PrimaryButton(
            text = stringResource(R.string.button_save),
            state = state.saveButtonState,
            onClick = saveClicked,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.canHide) {
            if (state.isShowed) {
                SecondaryButton(
                    text = stringResource(R.string.button_hide),
                    onClick = hideClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                SecondaryButton(
                    text = stringResource(R.string.button_show),
                    onClick = showClicked,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.fields.forEach {
                when (it) {
                    is PetField.SimplePetField -> PetEditScreenSimpleField(
                        field = it,
                        fieldUpdated = fieldUpdated,
                        fieldDeleted = fieldDeleted
                    )
                    is PetField.EnumPetField -> PetEditScreenEnumField(
                        field = it,
                        fieldUpdated = fieldUpdated,
                        fieldDeleted = fieldDeleted
                    )
                    is PetField.DatePetField -> PetEditScreenDateField(
                        field = it,
                        dateFormatter = dateFormatter,
                        fieldUpdated = fieldUpdated,
                        fieldDeleted = fieldDeleted
                    )
                    is PetField.ListPetField -> PetEditScreenListField(
                        field = it,
                        fieldUpdated = fieldUpdated,
                        fieldDeleted = fieldDeleted
                    )
                    is PetField.AchievementPetField -> PetEditScreenAchievementsField(
                        field = it,
                        fieldUpdated = fieldUpdated,
                        fieldDeleted = fieldDeleted
                    )
                }
            }

            var addFieldsDialogShowed by remember { mutableStateOf(false) }

            if (addFieldsDialogShowed) {
                MultiSelectionDialog(
                    items = state.unusedFields,
                    onItemsSelected = { if (it.isNotEmpty()) fieldsAdded(it) },
                    itemToTextMapper = { stringResource(it.titleRes) },
                    onDismiss = { addFieldsDialogShowed = false },
                    acceptButtonText = stringResource(R.string.button_add),
                    title = stringResource(R.string.add_fields_title),
                    subtitle = stringResource(R.string.add_fields_subtitle)
                )
            }

            if (state.unusedFields.isNotEmpty()) {
                SecondaryButton(
                    text = stringResource(R.string.button_add),
                    onClick = { addFieldsDialogShowed = true },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PetEditScreenSimpleField(
    field: PetField.SimplePetField,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit
) {
    PetDescriptionContainer(
        title = stringResource(field.titleRes),
        isValid = field.valid,
        canDelete = field.canDelete,
        deleteClicked = { fieldDeleted(field) }
    ) {
        TextField(
            state = field.textField,
            onValueChange = {
                val newState = field.textField.copy(text = TextModel(it), isIncorrect = false)
                fieldUpdated(field.copy(textField = newState).validated())
            },
            placeholder = field.hintRes?.let { stringResource(it) } ?: "",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PetEditScreenEnumField(
    field: PetField.EnumPetField,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit
) {
    PetDescriptionContainer(
        title = stringResource(field.titleRes),
        isValid = field.valid,
        canDelete = field.canDelete,
        deleteClicked = { fieldDeleted(field) }
    ) {
        Dropdown(
            target = { showMenu ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Base500, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                        .clickable { showMenu.value = !showMenu.value }
                ) {
                    Text(
                        text = stringResource(field.selectedRes()),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(icon = UiKitR.drawable.ic_down)
                }
            },
            items = field.enum.values.filter { it.second != field.selectedKey }
                .map {
                    DropdownItem(
                        text = stringResource(it.first),
                        onSelected = {
                            fieldUpdated(field.copy(selectedKey = it.second))
                        }
                    )
                }
        )
    }
}

@Composable
private fun PetEditScreenDateField(
    field: PetField.DatePetField,
    dateFormatter: DateFormatter,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit
) {
    PetDescriptionContainer(
        title = stringResource(field.titleRes),
        isValid = field.valid,
        canDelete = field.canDelete,
        deleteClicked = { fieldDeleted(field) }
    ) {
        val formatted = remember(field.date) { field.date?.let(dateFormatter::format) }

        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            CalendarDialog(
                dateSelected = {
                    fieldUpdated(field.copy(date = it).validated())
                },
                onDismiss = { showDialog = false }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Base500, RoundedCornerShape(4.dp))
                .padding(8.dp)
                .clickable { showDialog = true }
        ) {
            Text(
                text = formatted ?: stringResource(R.string.select_date),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.weight(1f)
            )

            Icon(icon = UiKitR.drawable.ic_calendar)
        }
    }
}

@Composable
private fun PetEditScreenListField(
    field: PetField.ListPetField,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit
) {
    PetDescriptionContainer(
        title = stringResource(field.titleRes),
        isValid = field.valid,
        canDelete = field.canDelete,
        deleteClicked = { fieldDeleted(field) }
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            field.list.forEachIndexed { i, state ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        state = state,
                        onValueChange = {
                            val newState = state.copy(text = TextModel(it), isIncorrect = false)
                            val newList = field.list.toMutableList()
                            newList[i] = newState
                            fieldUpdated(field.copy(list = newList).validated())
                        },
                        modifier = Modifier.weight(1f)
                    )

                    ClickableIcon(
                        icon = UiKitR.drawable.ic_close,
                        onClick = {
                            val newList = field.list.toMutableList()
                            newList.removeAt(i)
                            fieldUpdated(field.copy(list = newList).validated())
                        },
                        tint = MaterialTheme.colors.error
                    )
                }
            }

            SecondaryButton(
                text = stringResource(R.string.button_add),
                onClick = {
                    val newList = field.list.toMutableList()
                    newList.add(TextFieldState())
                    fieldUpdated(field.copy(list = newList))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PetEditScreenAchievementsField(
    field: PetField.AchievementPetField,
    fieldUpdated: (PetField) -> Unit,
    fieldDeleted: (PetField) -> Unit
) {
    PetDescriptionContainer(
        title = stringResource(field.titleRes),
        isValid = field.valid,
        canDelete = field.canDelete,
        deleteClicked = { fieldDeleted(field) }
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            field.map.entries.toList()
                .sortedBy { it.value.ordinal }
                .forEach { (competition, level) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            state = competition,
                            onValueChange = {
                                val newState = competition.copy(text = TextModel(it), isIncorrect = false)
                                val newMap = field.map.toMutableMap()
                                newMap.remove(competition)
                                newMap[newState] = level
                                fieldUpdated(field.copy(map = newMap).validated())
                            },
                            modifier = Modifier.weight(1f)
                        )

                        var dialogShow by remember { mutableStateOf(false) }

                        if (dialogShow) {
                            SelectorDialog(
                                items = AchievementLevel.values().toList(),
                                itemToTextMapper = { level ->
                                    val res = when (level) {
                                        AchievementLevel.WINNER -> R.string.level_champion
                                        AchievementLevel.MEDALIST -> R.string.level_winner
                                        AchievementLevel.PARTICIPANT -> R.string.level_participant
                                    }
                                    stringResource(res)
                                },
                                onItemSelected = {
                                    val newMap = field.map.toMutableMap()
                                    newMap[competition] = it
                                    fieldUpdated(field.copy(map = newMap))
                                },
                                onDismiss = { dialogShow = false }
                            )
                        }

                        Box(
                            modifier = Modifier
                                .border(1.dp, Base500, RoundedCornerShape(4.dp))
                                .clickable { dialogShow = true }
                                .padding(8.dp)
                        ) {
                            Icon(
                                icon = UiKitR.drawable.ic_award,
                                tint = level.iconTint()
                            )
                        }

                        ClickableIcon(
                            icon = UiKitR.drawable.ic_close,
                            onClick = {
                                val newMap = field.map.toMutableMap()
                                newMap.remove(competition)
                                fieldUpdated(field.copy(map = newMap).validated())
                            },
                            tint = MaterialTheme.colors.error
                        )
                    }
                }

            SecondaryButton(
                text = stringResource(R.string.button_add),
                onClick = {
                    val newMap = field.map.toMutableMap()
                    newMap[TextFieldState()] = AchievementLevel.WINNER
                    fieldUpdated(field.copy(map = newMap))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PetEditScreen_Preview() {
    PetterAppTheme {
        val state = PetEditUiState(
            titleRes = R.string.title_edit,
            modelStatus = DataState.Content(
                PetEditFullUiModel(
                    photo = null,
                    isShowed = true,
                    canHide = true,
                    saveButtonState = ButtonState(),
                    fields = listOf(
                        PetField.ListPetField(
                            titleRes = R.string.vaccinations,
                            fieldName = PetFieldName.VACCINATIONS,
                            list = listOf(
                                TextFieldState("Грипп"),
                                TextFieldState("Гепатит"),
                                TextFieldState("В лапу")
                            )
                        ),
                        PetField.AchievementPetField(
                            titleRes = R.string.achievements,
                            map = mapOf(
                                TextFieldState("Мистер сапожек") to AchievementLevel.WINNER,
                                TextFieldState("Азамат Мусагалиев") to AchievementLevel.PARTICIPANT
                            )
                        ),
                        PetField.SimplePetField(
                            titleRes = R.string.breed,
                            fieldName = PetFieldName.BREED,
                            textField = TextFieldState("Бигль")
                        ),
                        PetField.EnumPetField(
                            titleRes = R.string.gender,
                            fieldName = PetFieldName.GENDER,
                            enum = PetEnum(
                                listOf(
                                    R.string.gender_male to Gender.MALE.name
                                )
                            )
                        ),
                        PetField.DatePetField(
                            titleRes = R.string.birth_date,
                            fieldName = PetFieldName.BIRTH_DATE,
                            date = LocalDate.of(2012, 3, 12)
                        )
                    ),
                    unusedFields = listOf()
                )
            )
        )

        PetEditScreen(
            state = state,
            dateFormatter = SimpleDateFormatter(),
            backClicked = {},
            saveClicked = {},
            showClicked = {},
            hideClicked = {},
            avatarClicked = {},
            fieldUpdated = {},
            fieldDeleted = {},
            fieldsAdded = {},
            reloadClicked = {}
        )
    }
}
