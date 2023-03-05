package ru.gortea.petter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import ru.gortea.petter.arch.android.compose.LocalApplicationContext
import ru.gortea.petter.auth.registration.ui.RegistrationScreen
import ru.gortea.petter.theme.PetterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalApplicationContext provides applicationContext) {
                PetterAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        RegistrationScreen()
                    }
                }
            }
        }
    }
}
