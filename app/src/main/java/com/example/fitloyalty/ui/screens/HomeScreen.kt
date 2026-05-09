package com.example.fitloyalty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.data.local.PointsDataStore
import kotlinx.coroutines.launch

fun getLevel(points: Int): String {
    return when {
        points >= 1000 -> "Diamond"
        points >= 700 -> "Emerald"
        points >= 500 -> "Platinum"
        points >= 300 -> "Gold"
        points >= 100 -> "Silver"
        else -> "Bronze"
    }
}

fun getNextLevel(points: Int): String {
    return when {
        points < 100 -> "Silver"
        points < 300 -> "Gold"
        points < 500 -> "Platinum"
        points < 700 -> "Emerald"
        points < 1000 -> "Diamond"
        else -> "MAX LEVEL"
    }
}

fun getLevelProgress(points: Int): Float {
    val currentStart: Int
    val nextTarget: Int

    when {
        points < 100 -> {
            currentStart = 0
            nextTarget = 100
        }

        points < 300 -> {
            currentStart = 100
            nextTarget = 300
        }

        points < 500 -> {
            currentStart = 300
            nextTarget = 500
        }

        points < 700 -> {
            currentStart = 500
            nextTarget = 700
        }

        points < 1000 -> {
            currentStart = 700
            nextTarget = 1000
        }

        else -> return 1f
    }

    return ((points - currentStart).toFloat() / (nextTarget - currentStart).toFloat())
        .coerceIn(0f, 1f)
}

fun getPointsToNextLevel(points: Int): Int {
    return when {
        points < 100 -> 100 - points
        points < 300 -> 300 - points
        points < 500 -> 500 - points
        points < 700 -> 700 - points
        points < 1000 -> 1000 - points
        else -> 0
    }
}

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val pointsDataStore = remember { PointsDataStore(context) }
    val scope = rememberCoroutineScope()

    val points by pointsDataStore.pointsFlow.collectAsState(initial = 0)

    val level = getLevel(points)
    val nextLevel = getNextLevel(points)
    val progress = getLevelProgress(points)
    val pointsToNext = getPointsToNextLevel(points)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F4EF))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(185.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF111111),
                                Color(0xFF2A2A2A)
                            )
                        ),
                        shape = RoundedCornerShape(30.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "FitLoyalty",
                            color = Color.White,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Premium gym rewards",
                            color = Color(0xFFD4AF37),
                            fontSize = 15.sp
                        )
                    }

                    Column {
                        Text(
                            text = "$points pkt",
                            color = Color.White,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Poziom: $level",
                            color = Color(0xFFD4AF37),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(22.dp)) {

                    Text(
                        text = "Postęp do poziomu $nextLevel",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp),
                        color = Color(0xFFD4AF37),
                        trackColor = Color(0xFFE7E2D5)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (pointsToNext > 0) {
                            "Brakuje $pointsToNext pkt do poziomu $nextLevel"
                        } else {
                            "Osiągnięto maksymalny poziom"
                        },
                        fontSize = 15.sp,
                        color = Color(0xFF666666)
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("Wizyty", "${points / 10}", Modifier.weight(1f))
                StatCard("Status", "Premium", Modifier.weight(1f))
                StatCard("Bonus", "+10 pkt", Modifier.weight(1f))
            }
        }

        item {
            Button(
                onClick = {
                    scope.launch {
                        pointsDataStore.addVisit("Użytkownik")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD4AF37),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Dodaj wizytę +10 pkt",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Aktualny cel",
                            fontSize = 15.sp,
                            color = Color(0xFF666666)
                        )

                        Text(
                            text = if (pointsToNext > 0) nextLevel else "MAX LEVEL",
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111111)
                        )
                    }

                    Text(
                        text = "🏆",
                        fontSize = 42.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                color = Color(0xFF777777)
            )

            Text(
                text = value,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )
        }
    }
}