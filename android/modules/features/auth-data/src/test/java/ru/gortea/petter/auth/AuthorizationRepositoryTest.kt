package ru.gortea.petter.auth

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import ru.gortea.petter.auth.data.model.CredsAuthorizationModel
import ru.gortea.petter.auth.stubs.TokenRepositoryStub
import ru.gortea.petter.data.model.DataState

internal class AuthorizationRepositoryTest : FunSpec({

    test("Success auth by creds") {
        val tokenRepository = TokenRepositoryStub()
        val authRepo = TestableAuthRepositoryFactory.createAuthRepository(tokenRepository)

        authRepo.get().test {
            skipItems(1)
            authRepo.invalidate(CredsAuthorizationModel("", "", ""))
            skipItems(1)
            val item = awaitItem() as DataState.Content
            assertEquals("creds_id", item.content.id)
            assertTrue(tokenRepository.getToken().isNotEmpty())
        }
    }

    test("Success auth by token") {
        val tokenRepository = TokenRepositoryStub().apply { updateToken("token") }
        val authRepo = TestableAuthRepositoryFactory.createAuthRepository(tokenRepository)

        authRepo.get().test {
            skipItems(1)
            authRepo.invalidate()
            skipItems(1)
            val item = awaitItem() as DataState.Content
            assertEquals("refresh_token_id", item.content.id)
            assertTrue(tokenRepository.getToken().isNotEmpty())
        }
    }

    test("Auth failed by access denied") {
        val tokenRepository = TokenRepositoryStub().apply { updateToken("token") }
        val authRepo = TestableAuthRepositoryFactory.createFailedAuthRepository(tokenRepository)

        authRepo.get().test {
            skipItems(1)
            authRepo.invalidate()
            skipItems(1)
            val item = awaitItem()
            assertInstanceOf(DataState.Fail::class.java, item)
            assertTrue(tokenRepository.getToken().isEmpty())
        }
    }

    test("Auth failed by connection error") {
        val tokenRepository = TokenRepositoryStub().apply { updateToken("token") }
        val authRepo = TestableAuthRepositoryFactory.createFailedAuthRepository(tokenRepository)

        authRepo.get().test {
            skipItems(1)
            authRepo.invalidate(CredsAuthorizationModel("", "", ""))
            skipItems(1)
            val item = awaitItem()
            assertInstanceOf(DataState.Fail::class.java, item)
            assertTrue(tokenRepository.getToken().isNotEmpty())
        }
    }
})
