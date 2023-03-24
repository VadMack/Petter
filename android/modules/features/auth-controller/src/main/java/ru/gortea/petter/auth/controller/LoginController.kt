package ru.gortea.petter.auth.controller

import ru.gortea.petter.auth.controller.model.AuthorizedUserModel

interface LoginController {
    fun login(userModel: AuthorizedUserModel)
}
