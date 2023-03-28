package ru.gortea.petter.data.util

import java.io.File

interface ContentFileConverter {
    fun fileFromContent(path: String): File?
}
