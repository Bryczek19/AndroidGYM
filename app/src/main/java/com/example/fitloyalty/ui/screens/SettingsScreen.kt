package com.example.fitloyalty.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.viewmodel.PointsViewModel

@Composable
fun SettingsScreen(pointsViewModel: PointsViewModel) {

    val darkModeEnabled by pointsViewModel.darkMode.collectAsState()
    val notificationsEnabled by pointsViewModel.notifications.collectAsState()

    val backgroundColor = if (darkModeEnabled) Color(0xFF111111) else Color(0xFFF5F2EA)
    val cardColor = if (darkModeEnabled) Color(0xFF1F1F1F) else Color.White
    val textColor = if (darkModeEnabled) Color.White else Color(0xFF111111)
    val subTextColor = if (darkModeEnabled) Color(0xFFBBBBBB) else Color(0xFF666666)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFF101010),
                                Color(0xFF1E1E1E),
                                Color(0xFF2C2412)
                            )
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(22.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Ustawienia",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Konfiguracja aplikacji FitLoyalty",
                            fontSize = 15.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Text(
                        text = if (darkModeEnabled) "Tryb ciemny aktywny" else "Tryb jasny aktywny",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        text = "Preferencje",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SettingRow(
                        title = "Powiadomienia",
                        description = "Informacje o wizytach i nagrodach",
                        checked = notificationsEnabled,
                        textColor = textColor,
                        subTextColor = subTextColor,
                        onCheckedChange = { enabled ->
                            pointsViewModel.saveNotifications(enabled)
                        }
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    SettingRow(
                        title = "Tryb ciemny",
                        description = "Zmiana motywu aplikacji",
                        checked = darkModeEnabled,
                        textColor = textColor,
                        subTextColor = subTextColor,
                        onCheckedChange = { enabled ->
                            pointsViewModel.saveDarkMode(enabled)
                        }
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        text = "Dane aplikacji",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Aplikacja zapisuje punkty, historię wizyt, nagrody oraz ustawienia lokalnie w DataStore i synchronizuje dane z Firebase.",
                        fontSize = 15.sp,
                        lineHeight = 21.sp,
                        color = subTextColor
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    pointsViewModel.resetSettings()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(20.dp),
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

        item {
            Button(
                onClick = {
                    pointsViewModel.resetAllData()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (darkModeEnabled) Color(0xFF2A2A2A) else Color(0xFF111111),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Reset punktów i historii",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    description: String,
    checked: Boolean,
    textColor: Color,
    subTextColor: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = subTextColor
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}