package ru.gortea.petter.auth.controller

import kotlinx.coroutines.flow.StateFlow

interface AuthObservable {
    fun isAuthorized(): StateFlow<Boolean>
}
