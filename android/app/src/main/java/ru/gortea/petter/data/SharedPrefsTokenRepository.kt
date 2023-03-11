package ru.gortea.petter.data

import android.content.Context
import androidx.core.content.edit
import ru.gortea.petter.token.storage.JwtTokenRepository

class SharedPrefsTokenRepository(
    context: Context
) : JwtTokenRepository {
    private val prefs = context.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE)

    override fun updateToken(newValue: String) {
        prefs.edit { putString(TOKEN_KEY, newValue) }
    }

    override fun getToken(): String {
        return prefs.getString(TOKEN_KEY, "") ?: ""
    }

    private companion object {
        private const val TOKEN_PREFS = "TOKEN_PREFS"
        private const val TOKEN_KEY = "TOKEN_KEY"
    }
}
