package ru.gortea.petter.pet.stubs

import ru.gortea.petter.data.util.ContentFileConverter
import java.io.File

internal class ContentFileConverterStub : ContentFileConverter {
    override fun fileFromContent(path: String): File? = null
}
