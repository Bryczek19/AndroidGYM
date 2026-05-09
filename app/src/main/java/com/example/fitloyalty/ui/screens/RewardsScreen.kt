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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.utils.NotificationHelper
import com.example.fitloyalty.viewmodel.PointsViewModel

@Composable
fun RewardsScreen(pointsViewModel: PointsViewModel) {

    val context = LocalContext.current

    val notificationHelper = remember {
        NotificationHelper(context)
    }

    val points by pointsViewModel.points.collectAsState()
    val rewards by pointsViewModel.rewards.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F2EA))
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
                            text = "Nagrody",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Wymieniaj punkty na benefity premium",
                            fontSize = 15.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Text(
                        text = "$points pkt dostępne",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            RewardCard(
                title = "Darmowy shake",
                cost = 50,
                points = points,
                description = "Nagroda za regularne wizyty.",
                tag = "Popularne",
                onRedeem = {
                    pointsViewModel.redeemReward("Darmowy shake", 50)
                    notificationHelper.showRewardNotification("Darmowy shake")
                }
            )
        }

        item {
            RewardCard(
                title = "Wejście na saunę",
                cost = 100,
                points = points,
                description = "Jednorazowe wejście do strefy regeneracji.",
                tag = "Regeneracja",
                onRedeem = {
                    pointsViewModel.redeemReward("Wejście na saunę", 100)
                    notificationHelper.showRewardNotification("Wejście na saunę")
                }
            )
        }

        item {
            RewardCard(
                title = "Zniżka na karnet",
                cost = 250,
                points = points,
                description = "Rabat na kolejny miesiąc członkostwa.",
                tag = "Oszczędność",
                onRedeem = {
                    pointsViewModel.redeemReward("Zniżka na karnet", 250)
                    notificationHelper.showRewardNotification("Zniżka na karnet")
                }
            )
        }

        item {
            RewardCard(
                title = "Trening personalny",
                cost = 500,
                points = points,
                description = "Indywidualna konsultacja z trenerem.",
                tag = "Premium",
                onRedeem = {
                    pointsViewModel.redeemReward("Trening personalny", 500)
                    notificationHelper.showRewardNotification("Trening personalny")
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Historia odebranych nagród",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )
        }

        if (rewards.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Brak odebranych nagród",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111111)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Zbieraj punkty za wizyty i odbieraj benefity.",
                            fontSize = 15.sp,
                            color = Color(0xFF777777)
                        )
                    }
                }
            }
        } else {
            rewards.forEach { reward ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = reward,
                            modifier = Modifier.padding(18.dp),
                            color = Color(0xFF111111),
                            fontSize = 15.sp
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
fun RewardCard(
    title: String,
    cost: Int,
    points: Int,
    description: String,
    tag: String,
    onRedeem: () -> Unit
) {
    val available = points >= cost
    val progress = (points.toFloat() / cost.toFloat()).coerceIn(0f, 1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = description,
                        fontSize = 15.sp,
                        color = Color(0xFF666666)
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            if (available) Color(0xFFE9F7E8) else Color(0xFFF2EEE3),
                            RoundedCornerShape(100.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = if (available) "Dostępne" else tag,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (available) Color(0xFF1D7A32) else Color(0xFF8A6D1D)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Koszt: $cost pkt",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFD4AF37)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = Color(0xFFD4AF37),
                trackColor = Color(0xFFE9E3D2)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (available) {
                    "Możesz odebrać tę nagrodę teraz."
                } else {
                    "Brakuje ${cost - points} pkt do odebrania."
                },
                fontSize = 14.sp,
                color = Color(0xFF777777)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRedeem,
                enabled = available,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD4AF37),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xFFE1E1E1),
                    disabledContentColor = Color(0xFF777777)
                )
            ) {
                Text(
                    text = if (available) "Odbierz nagrodę" else "Za mało punktów",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}