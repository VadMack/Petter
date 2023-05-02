package ru.gortea.petter.network.errors

fun Throwable.isConnectionError(): Boolean {
    return this is PetterNetworkError.RequestError
}
