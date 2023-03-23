package ru.gortea.petter.profile.data.util

import java.io.File

interface ContentFileConverter {
    fun fileFromContent(path: String): File?
}
