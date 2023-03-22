package ru.gortea.petter.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.gortea.petter.arch.android.activity.getComponent
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.main.MainActivity
import ru.gortea.petter.splash.di.SplashComponent

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }

        super.onCreate(savedInstanceState)
        addOnContextAvailableListener {
            authorizeByToken()
        }
    }

    private fun authorizeByToken() {
        lifecycleScope.launchWhenCreated {
            val repository = getComponent<SplashComponent>().authRepository
            repository.invalidate()
            launch {
                repository.get()
                    .onEach {
                        println("xxx: $it")
                        when (it) {
                            is DataState.Content, is DataState.Fail -> openMainActivity()
                            is DataState.Loading, DataState.Empty -> Unit
                        }
                    }
                    .collect()
            }
        }
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
