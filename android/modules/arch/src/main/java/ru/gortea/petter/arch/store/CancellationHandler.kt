package ru.gortea.petter.arch.store

interface CancellationHandler<State: Any> {
    suspend fun onCancel(state: State)
}
