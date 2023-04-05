package ru.gortea.petter.home.navigation.commands

import ru.gortea.petter.navigation.NavCommand
import ru.gortea.petter.pet.list.model.PetListKeyModel

internal sealed interface HomeNavCommand : NavCommand {
    class AcceptKeyModel(val keyModel: PetListKeyModel) : HomeNavCommand
}
