package ru.gortea.petter.pet

import app.cash.turbine.test
import io.kotest.core.coroutines.backgroundScope
import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.pet.di.TestPetComponent
import ru.gortea.petter.pet.presentation.PetUiEvent
import ru.gortea.petter.pet.presentation.createPetStore
import ru.gortea.petter.pet.presentation.state.PetField
import ru.gortea.petter.pet.presentation.state.PetFieldName
import ru.gortea.petter.pet.presentation.state.PetState

internal class PetStoreTest : FunSpec({
    coroutineTestScope = true
    val component = TestPetComponent()

    test("Edit mode enabled") {
        val store = createPetStore("", true, component, Router.stub())
        store.attach(backgroundScope)

        store.stateFlow.test {
            var item: PetState = awaitItem()
            while (!item.petLoadingStatus.isContent) {
                item = awaitItem()
            }

            val fields = (item.petLoadingStatus as DataState.Content).content.fields

            assertEquals(PetFieldName.NAME, fields.first().fieldName)
        }
    }

    test("Edit mode disabled") {
        val store = createPetStore("", false, component, Router.stub())
        store.attach(backgroundScope)

        store.stateFlow.test {
            var item: PetState = awaitItem()
            while (!item.petLoadingStatus.isContent) {
                item = awaitItem()
            }

            val fields = (item.petLoadingStatus as DataState.Content).content.fields

            assertEquals(PetFieldName.BREED, fields.first().fieldName)
        }
    }

    test("Add fields") {
        val store = createPetStore("", true, component, Router.stub())
        store.attach(backgroundScope)

        store.stateFlow.test {
            var item: PetState = awaitItem()
            while (!item.petLoadingStatus.isContent) {
                item = awaitItem()
            }

            var fields = (item.petLoadingStatus as DataState.Content).content.fields

            assertTrue(fields.find { it.fieldName == PetFieldName.ACHIEVEMENTS } == null)

            store.dispatch(
                PetUiEvent.AddFields(
                    listOf(
                        PetField.AchievementPetField(0, emptyMap())
                    )
                )
            )

            fields = (awaitItem().petLoadingStatus as DataState.Content).content.fields

            assertTrue(fields.find { it.fieldName == PetFieldName.ACHIEVEMENTS } != null)
        }
    }
})
