package ru.gortea.petter.profile.ui.mapper

import android.net.Uri
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.profile.presentation.ProfileState
import ru.gortea.petter.profile.ui.state.ProfileUiState
import ru.gortea.petter.profile.ui.state.ProfileUserModel

internal class ProfileUiStateMapper : UiStateMapper<ProfileState, ProfileUiState> {

    override fun map(state: ProfileState): ProfileUiState {
        return ProfileUiState(
            hasProfileMenu = state.isCurrentUser,
            userState = state.userModelStatus.mapContentSync {
                ProfileUserModel(
                    avatar = it.avatarPath?.let(Uri::parse),
                    name = it.displayName ?: "",
                    address = it.address?.toString()
                )
            }
        )
    }
}
