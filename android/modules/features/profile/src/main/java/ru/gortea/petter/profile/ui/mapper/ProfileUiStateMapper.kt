package ru.gortea.petter.profile.ui.mapper

import android.net.Uri
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.profile.presentation.ProfileState
import ru.gortea.petter.profile.ui.state.ProfileUiModel
import ru.gortea.petter.profile.ui.state.ProfileUiState

internal class ProfileUiStateMapper : UiStateMapper<ProfileState, ProfileUiState> {

    override fun map(state: ProfileState): ProfileUiState {
        return ProfileUiState(
            hasProfileMenu = state.isCurrentUser,
            userState = state.userModelStatus.mapContentSync {
                ProfileUiModel(
                    id = it.id,
                    avatar = it.avatarPath?.let(Uri::parse),
                    name = it.displayName ?: "",
                    address = it.address?.toString(),
                    canAddPet = state.isCurrentUser
                )
            }
        )
    }
}
