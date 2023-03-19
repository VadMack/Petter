package ru.gortea.petter.network.errors

sealed class PetterNetworkError : Throwable() {
    data class RequestError(override val cause: Throwable) : PetterNetworkError()

    class InternalError : PetterNetworkError()
    class UserAlreadyExists : PetterNetworkError()
    class UnknownError : PetterNetworkError()
}
