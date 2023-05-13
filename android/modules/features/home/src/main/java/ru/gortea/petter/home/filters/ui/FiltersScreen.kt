package ru.gortea.petter.home.filters.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.arch.android.util.text
import ru.gortea.petter.arch.android.util.toTextFieldState
import ru.gortea.petter.home.R
import ru.gortea.petter.home.di.HomeComponent
import ru.gortea.petter.home.filters.presentation.FiltersState
import ru.gortea.petter.home.filters.presentation.FiltersUiEvent
import ru.gortea.petter.home.filters.presentation.constants.TitleHolder
import ru.gortea.petter.home.filters.presentation.createFilterStore
import ru.gortea.petter.home.filters.presentation.filters.EnumFilter
import ru.gortea.petter.home.filters.presentation.filters.Filter
import ru.gortea.petter.home.filters.presentation.filters.RangeFilter
import ru.gortea.petter.home.filters.presentation.filters.SimpleFieldFilter
import ru.gortea.petter.home.filters.ui.mapper.FiltersUiStateMapper
import ru.gortea.petter.home.filters.ui.state.FiltersUiState
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.dropdown.Dropdown
import ru.gortea.petter.ui_kit.dropdown.DropdownItem
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.separator.Separator
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.toolbar.CloseIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun FiltersScreen(
    router: Router<*>,
    keyModel: PetListKeyModel
) {
    val component = getComponent<HomeComponent>()
    val store by storeHolder {
        createFilterStore(router, keyModel, component)
    }

    store.collect(FiltersUiStateMapper()) { state ->
        FiltersScreen(
            state = state,
            acceptClicked = { store.dispatch(FiltersUiEvent.Accept) },
            backClicked = { store.dispatch(FiltersUiEvent.GoBack) },
            filterChanged = { store.dispatch(FiltersUiEvent.UpdateFilter(it)) }
        )
    }
}

@Composable
private fun FiltersScreen(
    state: FiltersUiState,
    acceptClicked: () -> Unit,
    backClicked: () -> Unit,
    filterChanged: (Filter) -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                startIcon = { CloseIcon(backClicked) },
                text = stringResource(R.string.filters),
                endIcon = {
                    ClickableIcon(
                        icon = UiKitR.drawable.ic_accept,
                        onClick = acceptClicked
                    )
                }
            )
        }
    ) {
        FiltersContent(
            state = state,
            filterChanged = filterChanged,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun FiltersContent(
    state: FiltersUiState,
    filterChanged: (Filter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(state.filters, key = { it.titleRes }) { filter ->
            when (filter) {
                is EnumFilter<*> -> EnumFilter(filter, filterChanged)
                is RangeFilter -> RangeFilter(filter, filterChanged)
                is SimpleFieldFilter -> SimpleFieldFilter(filter, filterChanged)
            }
            if (filter != state.filters.last()) {
                Separator()
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun EnumFilter(filter: EnumFilter<TitleHolder>, filterChanged: (Filter) -> Unit) {
    FilterContainer(title = stringResource(filter.titleRes)) {
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
                        text = stringResource(filter.selected.titleRes),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(icon = UiKitR.drawable.ic_down)
                }
            },
            items = filter.availableItems
                .map {
                    DropdownItem(
                        text = stringResource(it.titleRes),
                        onSelected = {
                            filterChanged(filter.copy(selected = it))
                        }
                    )
                }
        )
    }
}

@Composable
private fun RangeFilter(filter: RangeFilter, filterChanged: (Filter) -> Unit) {
    FilterContainer(title = stringResource(filter.titleRes)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                state = filter.minLimit.toTextFieldState(),
                placeholder = stringResource(R.string.filter_range_from),
                onValueChange = {
                    filterChanged(filter.changeMinLimitText(it))
                },
                modifier = Modifier.weight(1f)
            )

            TextField(
                state = filter.maxLimit.toTextFieldState(),
                placeholder = stringResource(R.string.filter_range_to),
                onValueChange = {
                    filterChanged(filter.changeMaxLimitText(it))
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun RangeFilter.changeMinLimitText(text: String): RangeFilter {
    return copy(minLimit = minLimit.text(text))
}

private fun RangeFilter.changeMaxLimitText(text: String): RangeFilter {
    return copy(maxLimit = maxLimit.text(text))
}

@Composable
private fun SimpleFieldFilter(filter: SimpleFieldFilter, filterChanged: (Filter) -> Unit) {
    FilterContainer(title = stringResource(filter.titleRes)) {
        TextField(
            state = filter.field.toTextFieldState(),
            placeholder = stringResource(filter.placeholderRes),
            onValueChange = {
                filterChanged(filter.changeText(it))
            }
        )
    }
}

private fun SimpleFieldFilter.changeText(text: String): SimpleFieldFilter {
    return copy(field = field.text(text))
}

@Composable
fun FilterContainer(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1.copy(color = Base500),
            modifier = Modifier.fillMaxWidth()
        )

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun FiltersScreen_Preview() {
    PetterAppTheme {
        val key = PetListKeyModel()
        val state = FiltersUiStateMapper().map(FiltersState.fromKeyModel(key))

        FiltersScreen(
            state = state,
            acceptClicked = {},
            backClicked = {},
            filterChanged = {}
        )
    }
}
