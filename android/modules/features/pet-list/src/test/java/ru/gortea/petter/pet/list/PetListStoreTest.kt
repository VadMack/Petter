package ru.gortea.petter.pet.list

import app.cash.turbine.test
import io.kotest.core.coroutines.backgroundScope
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import org.junit.jupiter.api.Assertions.assertEquals
import ru.gortea.petter.pet.list.di.TestComponent
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.list.presentation.PetListUiEvent
import ru.gortea.petter.pet.list.presentation.createPetListStore
import ru.gortea.petter.pet.list.state.TestStates
import ru.gortea.petter.pet.list.ui.mapper.PetListUiStateMapper

internal class PetListStoreTest : FunSpec({
    coroutineTestScope = true
    val component = TestComponent()
    val mapper = PetListUiStateMapper(component.dateFormatter)

    test("Move to liked/disliked state") {
        val store = createPetListStore(component = component, pageSize = 1, openPetCard = {})
        store.attach(backgroundScope)

        store.stateFlow
            .map(mapper::map)
            .test {
                skipItems(1)
                store.dispatch(PetListUiEvent.Invalidate(PetListKeyModel()))

                skipItems(3)

                store.dispatch(PetListUiEvent.LikePet("id"))

                assertEquals(TestStates.liked, awaitItem())

                store.dispatch(PetListUiEvent.DislikePet("id"))

                assertEquals(TestStates.disliked, awaitItem())
            }
    }

    test("Load page") {
        val store = createPetListStore(component = component, pageSize = 1, openPetCard = {})
        store.attach(backgroundScope)

        store.stateFlow
            .map(mapper::map)
            .test {
                skipItems(1)
                store.dispatch(PetListUiEvent.Invalidate(PetListKeyModel(pageSize = 1)))

                skipItems(3)

                delay(500)
                store.dispatch(PetListUiEvent.LoadPage)

                assertEquals(TestStates.pageLoading, awaitItem())
            }
    }

    test("My pet") {
        val store = createPetListStore(component = TestComponent("ownerId"), pageSize = 1, openPetCard = {})
        store.attach(backgroundScope)

        store.stateFlow
            .map(mapper::map)
            .test {
                skipItems(1)
                store.dispatch(PetListUiEvent.Invalidate(PetListKeyModel(pageSize = 1)))

                skipItems(3)

                delay(500)
                store.dispatch(PetListUiEvent.LoadPage)

                assertEquals(TestStates.myPet, awaitItem())
            }
    }
})
