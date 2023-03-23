package ru.gortea.petter.data

import android.content.Context
import coil.ImageLoader
import okhttp3.OkHttpClient

object PetterImageLoaderFactory {

    fun create(context: Context, client: OkHttpClient): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(client)
            .build()
    }
}
