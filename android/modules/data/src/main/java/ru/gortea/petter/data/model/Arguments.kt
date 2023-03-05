package ru.gortea.petter.data.model

interface Arguments {

    companion object {
        val empty: Arguments
            get() = object : Arguments {}
    }
}
