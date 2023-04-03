package ru.gortea.petter.pet.list.presentation

import ru.gortea.petter.pet.list.data.model.PetListKey

internal sealed interface PetListCommand {
    class Invalidate(val key: PetListKey) : PetListCommand
    class ChangeLikeStatus(val id: String, val like: Boolean) : PetListCommand

    object GetCurrentUser : PetListCommand
    object LoadPage : PetListCommand

    object InitPetList : PetListCommand
}
