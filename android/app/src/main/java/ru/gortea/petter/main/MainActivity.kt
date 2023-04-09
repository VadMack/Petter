package ru.gortea.petter.main

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import coil.compose.LocalImageLoader
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import ru.gortea.petter.R
import ru.gortea.petter.arch.android.activity.getComponent
import ru.gortea.petter.arch.android.compose.LocalApplicationContext
import ru.gortea.petter.main.di.MainActivityComponent
import ru.gortea.petter.navigation.PetterRootNode
import ru.gortea.petter.notifications.FirebaseNotifications
import ru.gortea.petter.theme.PetterAppTheme

class MainActivity : NodeActivity() {

    private val requestNotificationsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(
                this,
                R.string.need_notifications_permission_toast,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val notifications = FirebaseNotifications()

    private val googleApiAvailability by lazy { GoogleApiAvailability.getInstance() }

    private val imageLoader by lazy {
        getComponent<MainActivityComponent>().imageLoader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkGoogleServices()

        notifications.createChannel(
            this,
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        )
        notifications.initialToken()
        askNotificationPermission()
        setupInsets()
        setContent { Content() }
    }

    override fun onResume() {
        super.onResume()
        checkGoogleServices()
    }

    @Composable
    private fun Content() {
        CompositionLocalProvider(
            LocalApplicationContext provides applicationContext,
            LocalImageLoader provides imageLoader
        ) {
            PetterAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NodeHost(integrationPoint = appyxIntegrationPoint) {
                        PetterRootNode(buildContext = it)
                    }
                }
            }
        }
    }

    private fun checkGoogleServices() {
        if (googleApiAvailability.isAvailable(this)) {
            googleApiAvailability.makeGooglePlayServicesAvailable(this)
        }
    }

    private fun GoogleApiAvailability.isAvailable(context: Context): Boolean {
        return isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        if (hasPermission(POST_NOTIFICATIONS)) return

        if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
            AlertDialog.Builder(this)
                .setMessage(R.string.notifications_rationale)
                .setTitle(R.string.notifications_rationale_title)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    requestNotificationsPermissionLauncher.launch(POST_NOTIFICATIONS)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.no_thanks) { dialog, _ -> dialog.dismiss() }
                .show()
        } else {
            requestNotificationsPermissionLauncher.launch(POST_NOTIFICATIONS)
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
    }
}
