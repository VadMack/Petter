package ru.gortea.petter.token.storage.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JwtToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeviceToken
