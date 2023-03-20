package ru.gortea.petter.di.token.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JwtToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshToken
