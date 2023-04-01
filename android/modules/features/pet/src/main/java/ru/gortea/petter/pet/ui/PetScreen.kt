package ru.gortea.petter.pet.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ru.gortea.petter.pet.presentation.PetUiEvent
import ru.gortea.petter.pet.presentation.createPetStore
import ru.gortea.petter.pet.presentation.state.PetEnum
import ru.gortea.petter.pet.presentation.state.PetField
import ru.gortea.petter.pet.presentation.state.PetFieldName
import ru.gortea.petter.pet.ui.mapper.PetUiStateMapper
import ru.gortea.petter.pet.ui.mapper.iconTint
import ru.gortea.petter.pet.ui.state.showing.PetFullUiModel
import ru.gortea.petter.pet.ui.state.showing.PetUiModel
import ru.gortea.petter.pet.ui.state.showing.PetUiState
import ru.gortea.petter.theme.Male
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.avatar.Avatar
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.containers.PetDescriptionContainer
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.placeholder.LoadingPlaceholder
import ru.gortea.petter.ui_kit.text.TextWithIcon
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.toolbar.BackIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import java.time.LocalDate
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun PetScreen(
    id: String,
    router: PetterRouter<PetNavTarget>,
    needReload: Boolean
) {
    val component = getComponent<PetComponent>()
    val dateFormatter = remember { component.dateFormatter }
    val store by storeHolder {
        createPetStore(
            petId = id,
            editMode = false,
            component = component,
            router = router
        )
    }

    store.collect(PetUiStateMapper()) { state ->
        PetScreen(
            state = state,
            dateFormatter = dateFormatter,
            backClicked = { store.dispatch(PetUiEvent.GoBack) },
            deleteClicked = { store.dispatch(PetUiEvent.DeletePet) },
            editClicked = { store.dispatch(PetUiEvent.EditPet) },
            chatClicked = { store.dispatch(PetUiEvent.OpenChat) }
        )
    }
    
    LaunchedEffect(needReload) {
        store.dispatch(PetUiEvent.LoadPet(id))
    }
}

@Composable
internal fun PetScreen(
    state: PetUiState,
    dateFormatter: DateFormatter,
    backClicked: () -> Unit,
    deleteClicked: () -> Unit,
    editClicked: () -> Unit,
    chatClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                startIcon = { BackIcon(backClicked) },
                text = "",
                endIcon = if (state.canDelete) {
                    {
                        ClickableIcon(
                            icon = UiKitR.drawable.ic_delete,
                            onClick = deleteClicked,
                            tint = MaterialTheme.colors.error
                        )
                    }
                } else null
            )
        }
    ) {
        PetScreenRoot(
            state = state,
            dateFormatter = dateFormatter,
            editClicked = editClicked,
            chatClicked = chatClicked,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun PetScreenRoot(
    state: PetUiState,
    dateFormatter: DateFormatter,
    editClicked: () -> Unit,
    chatClicked: () -> Unit,
    modifier: Modifier
) {
    when (state.modelStatus) {
        is DataState.Empty -> Unit
        is DataState.Loading -> LoadingPlaceholder(modifier)
        is DataState.Content -> PetScreenContent(
            state = state.modelStatus.content,
            dateFormatter = dateFormatter,
            editClicked = editClicked,
            chatClicked = chatClicked,
            modifier = modifier
        )
        is DataState.Fail -> Unit // Todo add error placeholder
    }
}

@Composable
private fun PetScreenContent(
    state: PetFullUiModel,
    dateFormatter: DateFormatter,
    editClicked: () -> Unit,
    chatClicked: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(
            image = state.photo?.let {
                rememberAsyncImagePainter(
                    it,
                    placeholder = painterResource(UiKitR.drawable.ic_pet_placeholder),
                    error = painterResource(UiKitR.drawable.ic_pet_placeholder)
                )
            },
            placeholder = painterResource(UiKitR.drawable.ic_pet_placeholder),
            size = 158.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        TextWithIcon(
            text = state.model.name,
            style = MaterialTheme.typography.h3,
            trailingIcon = {
                Icon(
                    icon = state.model.genderIcon,
                    tint = state.model.genderTint,
                    size = 18.dp
                )
            },
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (state.canEdit) {
            PrimaryButton(
                text = stringResource(R.string.button_edit),
                onClick = editClicked,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            PrimaryButton(
                text = stringResource(R.string.button_chat),
                onClick = chatClicked,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.fields.forEach {
                when (it) {
                    is PetField.SimplePetField -> PetScreenSimpleField(it)
                    is PetField.EnumPetField -> PetScreenEnumField(it)
                    is PetField.DatePetField -> PetScreenDateField(it, dateFormatter)
                    is PetField.ListPetField -> PetScreenListField(it)
                    is PetField.AchievementPetField -> PetScreenAchievementsField(it)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PetScreenSimpleField(field: PetField.SimplePetField) {
    PetDescriptionContainer(title = stringResource(field.titleRes)) {

        Text(
            text = field.textField.text.getText(),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PetScreenEnumField(field: PetField.EnumPetField) {
    PetDescriptionContainer(title = stringResource(field.titleRes)) {

        Text(
            text = stringResource(field.selectedRes()),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PetScreenDateField(field: PetField.DatePetField, dateFormatter: DateFormatter) {
    PetDescriptionContainer(title = stringResource(field.titleRes)) {
        val formatted = remember(field.date) { dateFormatter.format(field.date!!) }

        Text(
            text = formatted,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PetScreenListField(field: PetField.ListPetField) {
    PetDescriptionContainer(title = stringResource(field.titleRes)) {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            field.list.forEach {
                Text(
                    text = it.text.getText(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PetScreenAchievementsField(field: PetField.AchievementPetField) {
    PetDescriptionContainer(title = stringResource(field.titleRes)) {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            field.map.entries.toList()
                .sortedBy { it.value.ordinal }
                .forEach { (competition, level) ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = competition.text.getText(),
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            icon = UiKitR.drawable.ic_award,
                            tint = level.iconTint(),
                            size = 20.dp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PetScreen_Preview() {
    PetterAppTheme {
        val state = PetUiState(
            canDelete = true,
            canEdit = true,
            modelStatus = DataState.Content(
                PetFullUiModel(
                    photo = null,
                    canEdit = true,
                    model = PetUiModel(
                        genderIcon = UiKitR.drawable.ic_male,
                        genderTint = Male,
                        name = "Нелли"
                    ),
                    fields = listOf(
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
                        ),
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
                        )
                    )
                )
            )
        )

        PetScreen(
            state = state,
            dateFormatter = SimpleDateFormatter(),
            backClicked = {},
            deleteClicked = {},
            editClicked = {},
            chatClicked = {}
        )
    }
}
