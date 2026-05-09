package com.example.fitloyalty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.data.local.PointsDataStore
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {

    val context = LocalContext.current
    val pointsDataStore = remember { PointsDataStore(context) }
    val scope = rememberCoroutineScope()

    val darkModeEnabled by pointsDataStore.darkModeFlow.collectAsState(initial = false)

    var notificationsEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    val backgroundColor = if (darkModeEnabled) Color(0xFF111111) else Color(0xFFF6F4EF)
    val cardColor = if (darkModeEnabled) Color(0xFF1F1F1F) else Color.White
    val textColor = if (darkModeEnabled) Color.White else Color(0xFF111111)
    val subTextColor = if (darkModeEnabled) Color(0xFFBBBBBB) else Color(0xFF666666)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(20.dp)
    ) {

        Text(
            text = "Ustawienia",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Text(
            text = "Konfiguracja aplikacji",
            fontSize = 17.sp,
            color = subTextColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor)
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {

                SettingRow(
                    title = "Powiadomienia",
                    checked = notificationsEnabled,
                    textColor = textColor,
                    onCheckedChange = {
                        notificationsEnabled = it
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                SettingRow(
                    title = "Tryb ciemny",
                    checked = darkModeEnabled,
                    textColor = textColor,
                    onCheckedChange = { enabled ->
                        scope.launch {
                            pointsDataStore.saveDarkMode(enabled)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    pointsDataStore.saveDarkMode(false)
                }
                notificationsEnabled = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD4AF37),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Reset ustawień",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    checked: Boolean,
    textColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = textColor
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}