package ru.gortea.petter.data

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.gortea.petter.data.impl.TestableFailSourceRepository
import ru.gortea.petter.data.impl.TestableSourceRepository
import ru.gortea.petter.data.model.DataState

class SourceRepositoryTest : FunSpec({

    test("Success loading") {
        val repository = TestableSourceRepository()

        launch {
            delay(1000)
            repository.invalidate()
        }

        repository.get().test {
            awaitItem() shouldBe DataState.Empty
            assert(awaitItem() is DataState.Loading)
            assert(awaitItem() is DataState.Content)
        }
    }

    test("Fail loading") {
        val repository = TestableFailSourceRepository()

        launch {
            delay(1000)
            repository.invalidate()
        }

        repository.get().test {
            awaitItem() shouldBe DataState.Empty
            assert(awaitItem() is DataState.Loading)
            assert(awaitItem() is DataState.Fail)
        }
    }

    test("Reload after success") {
        val repository = TestableSourceRepository()

        launch {
            delay(1000)
            repository.invalidate()
            delay(1000)
            repository.invalidate()
        }

        repository.get().test {
            awaitItem() shouldBe DataState.Empty
            assert(awaitItem() is DataState.Loading)
            assert(awaitItem() is DataState.Content)

            val item = awaitItem()
            assert(item is DataState.Content && item.refreshing)

            val completeItem = awaitItem()
            assert(completeItem is DataState.Content && !completeItem.refreshing)
        }
    }

    test("Reload after fail") {
        val repository = TestableFailSourceRepository()

        launch {
            delay(1000)
            repository.invalidate()
            delay(1000)
            repository.invalidate()
        }

        repository.get().test {
            awaitItem() shouldBe DataState.Empty
            assert(awaitItem() is DataState.Loading)
            assert(awaitItem() is DataState.Fail)
            assert(awaitItem() is DataState.Loading.WithError)
            assert(awaitItem() is DataState.Fail)
        }
    }

    test("Initial state") {
        val repository = TestableSourceRepository()

        repository.get().test {
            awaitItem() shouldBe DataState.Empty
        }
    }
})
