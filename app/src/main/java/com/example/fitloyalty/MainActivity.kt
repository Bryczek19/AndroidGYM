package com.example.fitloyalty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.fitloyalty.data.local.PointsDataStore
import com.example.fitloyalty.ui.navigation.MainNavigation
import com.example.fitloyalty.ui.screens.SplashScreen
import com.example.fitloyalty.ui.theme.FitLoyaltyTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current
            val pointsDataStore = remember { PointsDataStore(context) }
            val darkMode by pointsDataStore.darkModeFlow.collectAsState(initial = false)

            FitLoyaltyTheme(darkTheme = darkMode) {

                var showSplash by remember {
                    mutableStateOf(true)
                }

                LaunchedEffect(Unit) {
                    delay(2000)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    MainNavigation()
                }
            }
        }
    }
}