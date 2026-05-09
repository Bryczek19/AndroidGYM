package com.example.fitloyalty

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitloyalty.ui.navigation.MainNavigation
import com.example.fitloyalty.ui.screens.SplashScreen
import com.example.fitloyalty.ui.theme.FitLoyaltyTheme
import com.example.fitloyalty.utils.NotificationHelper
import com.example.fitloyalty.viewmodel.PointsViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        requestNotificationPermission()

        val notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel()

        setContent {
            FitLoyaltyTheme {

                var showSplash by remember {
                    mutableStateOf(true)
                }

                LaunchedEffect(Unit) {
                    delay(2800)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    val pointsViewModel: PointsViewModel = viewModel()

                    MainNavigation(
                        pointsViewModel = pointsViewModel
                    )
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }
}