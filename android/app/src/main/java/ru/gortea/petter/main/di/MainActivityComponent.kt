package ru.gortea.petter.main.di

import ru.gortea.petter.auth.controller.AuthObservable

interface MainActivityComponent {
    val authObservable: AuthObservable
}
