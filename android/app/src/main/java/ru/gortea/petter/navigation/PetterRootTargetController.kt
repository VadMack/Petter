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
    userRepo: CurrentUserRepository,
    coroutineScope: CoroutineScope
) {

    private val stateFlow = MutableStateFlow<PetterRootTarget>(PetterRootTarget.Content)

    private var authorized = true
    private var isEmpty = false

    init {
        coroutineScope.launch {
            launch {
                authObservable.isAuthorized()
                    .onEach {
                        authorized = it
                        updateTarget()
                    }
                    .collect()
            }

            launch {
                userRepo.isEmpty()
                    .onEach {
                        isEmpty = it
                        updateTarget()
                    }
                    .collect()
            }
        }
    }

    private fun updateTarget() {
        when {
            !authorized -> stateFlow.tryEmit(PetterRootTarget.Authorization)
            isEmpty -> stateFlow.tryEmit(PetterRootTarget.UserEdit)
            else -> stateFlow.tryEmit(PetterRootTarget.Authorization)
        }
    }

    fun get() = stateFlow.asStateFlow()
}
