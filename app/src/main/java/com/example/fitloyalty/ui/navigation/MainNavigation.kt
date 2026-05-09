package com.example.fitloyalty.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitloyalty.ui.screens.*

@Composable
fun MainNavigation() {

    var selectedScreen by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selectedScreen == 0,
                    onClick = { selectedScreen = 0 },
                    icon = { Icon(Icons.Default.Home, null, Modifier.size(30.dp)) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 1,
                    onClick = { selectedScreen = 1 },
                    icon = { Icon(Icons.Default.List, null, Modifier.size(30.dp)) },
                    label = { Text("Historia") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 2,
                    onClick = { selectedScreen = 2 },
                    icon = { Icon(Icons.Default.Star, null, Modifier.size(30.dp)) },
                    label = { Text("Nagrody") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 3,
                    onClick = { selectedScreen = 3 },
                    icon = { Icon(Icons.Default.Person, null, Modifier.size(30.dp)) },
                    label = { Text("Profil") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 4,
                    onClick = { selectedScreen = 4 },
                    icon = { Icon(Icons.Default.LocationOn, null, Modifier.size(30.dp)) },
                    label = { Text("Mapa") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 5,
                    onClick = { selectedScreen = 5 },
                    icon = { Icon(Icons.Default.Settings, null, Modifier.size(30.dp)) },
                    label = { Text("Opcje") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 6,
                    onClick = { selectedScreen = 6 },
                    icon = { Icon(Icons.Default.Info, null, Modifier.size(30.dp)) },
                    label = { Text("Info") }
                )
            }
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (selectedScreen) {
                0 -> HomeScreen()
                1 -> VisitsScreen()
                2 -> RewardsScreen()
                3 -> ProfileScreen()
                4 -> LocationScreen()
                5 -> SettingsScreen()
                6 -> AboutScreen()
            }
        }
    }
}