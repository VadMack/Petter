package ru.gortea.petter.auth.controller

import kotlinx.coroutines.flow.Flow

interface AuthObservable {
    fun isAuthorized(): Flow<Boolean>
}
