package ru.gortea.petter.data

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import ru.gortea.petter.data.impl.TestableFailSourceRepository
import ru.gortea.petter.data.impl.TestableSourceRepository
import ru.gortea.petter.data.model.DataState

class SourceRepositoryTest : FunSpec({

    test("Success loading") {
        val repository = TestableSourceRepository()

        repository.get().test {
            awaitItem() shouldBe DataState.Empty

            repository.invalidate()
            assertInstanceOf(DataState.Loading::class.java, awaitItem())
            assertInstanceOf(DataState.Content::class.java, awaitItem())
        }
    }

    test("Fail loading") {
        val repository = TestableFailSourceRepository()

        repository.get().test {
            awaitItem() shouldBe DataState.Empty

            repository.invalidate()
            assertInstanceOf(DataState.Loading::class.java, awaitItem())
            assertInstanceOf(DataState.Fail::class.java, awaitItem())
        }
    }

    test("Reload after success") {
        val repository = TestableSourceRepository()

        repository.get().test {
            awaitItem() shouldBe DataState.Empty

            repository.invalidate()
            assertInstanceOf(DataState.Loading::class.java, awaitItem())
            assertInstanceOf(DataState.Content::class.java, awaitItem())

            repository.invalidate()

            val item = awaitItem()
            assertInstanceOf(DataState.Content::class.java, item)
            item as DataState.Content
            assertTrue(item.refreshing)

            val completeItem = awaitItem()
            assertInstanceOf(DataState.Content::class.java, completeItem)
            completeItem as DataState.Content
            assertFalse(completeItem.refreshing)
        }
    }

    test("Reload after fail") {
        val repository = TestableFailSourceRepository()

        repository.get().test {
            awaitItem() shouldBe DataState.Empty

            repository.invalidate()
            assertInstanceOf(DataState.Loading::class.java, awaitItem())
            assertInstanceOf(DataState.Fail::class.java, awaitItem())

            repository.invalidate()
            assertInstanceOf(DataState.Loading.WithError::class.java, awaitItem())
            assertInstanceOf(DataState.Fail::class.java, awaitItem())
        }
    }

    test("Initial state") {
        val repository = TestableSourceRepository()

        repository.get().test {
            awaitItem() shouldBe DataState.Empty
        }
    }
})
