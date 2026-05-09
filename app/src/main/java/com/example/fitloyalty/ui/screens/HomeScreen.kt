package com.example.fitloyalty.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.utils.NotificationHelper
import com.example.fitloyalty.viewmodel.PointsViewModel

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

    return (
            (points - currentStart).toFloat() /
                    (nextTarget - currentStart).toFloat()
            ).coerceIn(0f, 1f)
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
fun HomeScreen(pointsViewModel: PointsViewModel) {

    val context = LocalContext.current

    var visible by remember { mutableStateOf(false) }
    var buttonScale by remember { mutableFloatStateOf(1f) }

    val notificationHelper = remember {
        NotificationHelper(context)
    }

    val points by pointsViewModel.points.collectAsState()

    val level = getLevel(points)
    val nextLevel = getNextLevel(points)
    val progress = getLevelProgress(points)
    val pointsToNext = getPointsToNextLevel(points)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "levelProgress"
    )

    val animatedButtonScale by animateFloatAsState(
        targetValue = buttonScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { }

    LaunchedEffect(Unit) {
        visible = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    LaunchedEffect(buttonScale) {
        if (buttonScale < 1f) {
            buttonScale = 1f
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F2EA))
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { -80 }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFF090909),
                                    Color(0xFF1B1B1B),
                                    Color(0xFF2C2412)
                                )
                            ),
                            shape = RoundedCornerShape(34.dp)
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
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
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        Color(0xFFD4AF37),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Level",
                                    tint = Color.Black,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "$points pkt",
                                color = Color.White,
                                fontSize = 44.sp,
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
        }

        item {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { 60 }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Postęp poziomu",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111111)
                                )

                                Text(
                                    text = "Cel: $nextLevel",
                                    fontSize = 15.sp,
                                    color = Color(0xFF777777)
                                )
                            }

                            Text(
                                text = "${(animatedProgress * 100).toInt()}%",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD4AF37)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(13.dp),
                            color = Color(0xFFD4AF37),
                            trackColor = Color(0xFFE9E3D2)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

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
        }

        item {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { 80 }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        title = "Wizyty",
                        value = "${points / 10}",
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Status",
                        value = "Premium",
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Bonus",
                        value = "+10 pkt",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        item {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { 100 }
            ) {
                Button(
                    onClick = {
                        buttonScale = 0.92f
                        pointsViewModel.addVisit()
                        notificationHelper.showVisitNotification(10)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .scale(animatedButtonScale)
                        .animateContentSize(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4AF37),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Dodaj wizytę  •  +10 pkt",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically { 120 }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
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
                                color = Color(0xFF777777)
                            )

                            Text(
                                text = if (pointsToNext > 0) nextLevel else "MAX LEVEL",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111111)
                            )

                            Text(
                                text = "Zbieraj punkty za regularne wizyty.",
                                fontSize = 14.sp,
                                color = Color(0xFF777777)
                            )
                        }

                        Text(
                            text = "🏆",
                            fontSize = 46.sp
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(98.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )
        }
    }
}