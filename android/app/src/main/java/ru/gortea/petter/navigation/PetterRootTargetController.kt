package ru.gortea.petter.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.gortea.petter.auth.controller.AuthObservable
import ru.gortea.petter.navigation.target.PetterRootTarget
import ru.gortea.petter.profile.data.local.CurrentUserRepository

internal class PetterRootTargetController(
    authObservable: AuthObservable,
    private val userRepo: CurrentUserRepository,
    coroutineScope: CoroutineScope
) {

    private val stateFlow = MutableStateFlow<PetterRootTarget>(PetterRootTarget.Authorization)

    init {
        coroutineScope.launch {
            launch {
                authObservable.isAuthorized()
                    .onEach { authorized ->
                        updateTarget(authorized)
                    }
                    .collect()
            }
        }
    }

    private suspend fun updateTarget(authorized: Boolean) {
        when {
            !authorized -> stateFlow.tryEmit(PetterRootTarget.Authorization)
            userRepo.isEmpty() -> stateFlow.tryEmit(PetterRootTarget.UserEdit)
            else -> stateFlow.tryEmit(PetterRootTarget.Content)
        }
    }

    fun get() = stateFlow.asStateFlow()
}
