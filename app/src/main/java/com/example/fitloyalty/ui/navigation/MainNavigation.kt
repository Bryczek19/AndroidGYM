package com.example.fitloyalty.ui.navigation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.fitloyalty.ui.screens.AboutScreen
import com.example.fitloyalty.ui.screens.HomeScreen
import com.example.fitloyalty.ui.screens.LocationScreen
import com.example.fitloyalty.ui.screens.ProfileScreen
import com.example.fitloyalty.ui.screens.RewardsScreen
import com.example.fitloyalty.ui.screens.SensorsScreen
import com.example.fitloyalty.ui.screens.SettingsScreen
import com.example.fitloyalty.ui.screens.VisitsScreen
import com.example.fitloyalty.viewmodel.PointsViewModel

@Composable
fun MainNavigation(pointsViewModel: PointsViewModel) {

    var selectedScreen by rememberSaveable {
        mutableStateOf(0)
    }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeNavigation(
            selectedScreen = selectedScreen,
            onScreenSelected = { selectedScreen = it },
            pointsViewModel = pointsViewModel
        )
    } else {
        PortraitNavigation(
            selectedScreen = selectedScreen,
            onScreenSelected = { selectedScreen = it },
            pointsViewModel = pointsViewModel
        )
    }
}

@Composable
fun PortraitNavigation(
    selectedScreen: Int,
    onScreenSelected: (Int) -> Unit,
    pointsViewModel: PointsViewModel
) {
    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selectedScreen == 0,
                    onClick = { onScreenSelected(0) },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 1,
                    onClick = { onScreenSelected(1) },
                    icon = {
                        Icon(
                            Icons.Default.List,
                            contentDescription = "Historia",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Historia") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 2,
                    onClick = { onScreenSelected(2) },
                    icon = {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Nagrody",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Nagrody") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 3,
                    onClick = { onScreenSelected(3) },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profil",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Profil") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 4,
                    onClick = { onScreenSelected(4) },
                    icon = {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Mapa",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Mapa") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 5,
                    onClick = { onScreenSelected(5) },
                    icon = {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Ruch",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Ruch") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 6,
                    onClick = { onScreenSelected(6) },
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Opcje",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Opcje") }
                )

                NavigationBarItem(
                    selected = selectedScreen == 7,
                    onClick = { onScreenSelected(7) },
                    icon = {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Info",
                            modifier = Modifier.size(28.dp)
                        )
                    },
                    label = { Text("Info") }
                )
            }
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            ScreenContent(
                selectedScreen = selectedScreen,
                pointsViewModel = pointsViewModel
            )
        }
    }
}

@Composable
fun LandscapeNavigation(
    selectedScreen: Int,
    onScreenSelected: (Int) -> Unit,
    pointsViewModel: PointsViewModel
) {
    Row {
        NavigationRail(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            NavigationRailItem(
                selected = selectedScreen == 0,
                onClick = { onScreenSelected(0) },
                icon = {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Home"
                    )
                },
                label = { Text("Home") }
            )

            NavigationRailItem(
                selected = selectedScreen == 1,
                onClick = { onScreenSelected(1) },
                icon = {
                    Icon(
                        Icons.Default.List,
                        contentDescription = "Historia"
                    )
                },
                label = { Text("Historia") }
            )

            NavigationRailItem(
                selected = selectedScreen == 2,
                onClick = { onScreenSelected(2) },
                icon = {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Nagrody"
                    )
                },
                label = { Text("Nagrody") }
            )

            NavigationRailItem(
                selected = selectedScreen == 3,
                onClick = { onScreenSelected(3) },
                icon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profil"
                    )
                },
                label = { Text("Profil") }
            )

            NavigationRailItem(
                selected = selectedScreen == 4,
                onClick = { onScreenSelected(4) },
                icon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Mapa"
                    )
                },
                label = { Text("Mapa") }
            )

            NavigationRailItem(
                selected = selectedScreen == 5,
                onClick = { onScreenSelected(5) },
                icon = {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Ruch"
                    )
                },
                label = { Text("Ruch") }
            )

            NavigationRailItem(
                selected = selectedScreen == 6,
                onClick = { onScreenSelected(6) },
                icon = {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Opcje"
                    )
                },
                label = { Text("Opcje") }
            )

            NavigationRailItem(
                selected = selectedScreen == 7,
                onClick = { onScreenSelected(7) },
                icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Info"
                    )
                },
                label = { Text("Info") }
            )
        }

        Surface(
            modifier = Modifier.weight(1f)
        ) {
            ScreenContent(
                selectedScreen = selectedScreen,
                pointsViewModel = pointsViewModel
            )
        }
    }
}

@Composable
fun ScreenContent(
    selectedScreen: Int,
    pointsViewModel: PointsViewModel
) {
    AnimatedContent(
        targetState = selectedScreen,
        transitionSpec = {
            if (targetState > initialState) {
                slideInHorizontally { width -> width } + fadeIn() togetherWith
                        slideOutHorizontally { width -> -width } + fadeOut()
            } else {
                slideInHorizontally { width -> -width } + fadeIn() togetherWith
                        slideOutHorizontally { width -> width } + fadeOut()
            }
        },
        label = "screenTransition"
    ) { screen ->
        when (screen) {
            0 -> HomeScreen(pointsViewModel)
            1 -> VisitsScreen(pointsViewModel)
            2 -> RewardsScreen(pointsViewModel)
            3 -> ProfileScreen(pointsViewModel)
            4 -> LocationScreen()
            5 -> SensorsScreen()
            6 -> SettingsScreen(pointsViewModel)
            7 -> AboutScreen()
        }
    }
}