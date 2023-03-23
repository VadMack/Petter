package ru.gortea.petter.data

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import ru.gortea.petter.profile.data.util.ContentFileConverter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class ContentResolverFileConverter(
    private val context: Context
) : ContentFileConverter {

    override fun fileFromContent(path: String): File? {
        val uri = Uri.parse(path)
        if (uri.scheme != "content") return null

        return fileFromContentUri(uri)
    }

    private fun fileFromContentUri(contentUri: Uri): File {
        // Preparing Temp file name
        val fileExtension = getFileExtension(contentUri)
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

        // Creating Temp file
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                copy(inputStream, oStream)
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    private fun getFileExtension(uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}
