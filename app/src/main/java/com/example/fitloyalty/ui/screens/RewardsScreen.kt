package com.example.fitloyalty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.data.local.PointsDataStore
import kotlinx.coroutines.launch

@Composable
fun RewardsScreen() {

    val context = LocalContext.current
    val pointsDataStore = remember { PointsDataStore(context) }
    val scope = rememberCoroutineScope()

    val points by pointsDataStore.pointsFlow.collectAsState(initial = 0)
    val rewards by pointsDataStore.rewardsFlow.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F4EF))
            .padding(20.dp)
    ) {

        Text(
            text = "Nagrody",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111111)
        )

        Text(
            text = "Twoje punkty: $points",
            fontSize = 18.sp,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            item {
                RewardCard(
                    title = "Darmowy shake",
                    cost = 50,
                    points = points,
                    onRedeem = {
                        scope.launch {
                            pointsDataStore.redeemReward("Darmowy shake", 50)
                        }
                    }
                )
            }

            item {
                RewardCard(
                    title = "Wejście na saunę",
                    cost = 100,
                    points = points,
                    onRedeem = {
                        scope.launch {
                            pointsDataStore.redeemReward("Wejście na saunę", 100)
                        }
                    }
                )
            }

            item {
                RewardCard(
                    title = "Zniżka na karnet",
                    cost = 250,
                    points = points,
                    onRedeem = {
                        scope.launch {
                            pointsDataStore.redeemReward("Zniżka na karnet", 250)
                        }
                    }
                )
            }

            item {
                RewardCard(
                    title = "Trening personalny",
                    cost = 500,
                    points = points,
                    onRedeem = {
                        scope.launch {
                            pointsDataStore.redeemReward("Trening personalny", 500)
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Odebrane nagrody",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111111)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (rewards.isEmpty()) {
                item {
                    Text(
                        text = "Brak odebranych nagród.",
                        color = Color(0xFF666666)
                    )
                }
            } else {
                rewards.forEach { reward ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Text(
                                text = reward,
                                modifier = Modifier.padding(16.dp),
                                color = Color(0xFF111111)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RewardCard(
    title: String,
    cost: Int,
    points: Int,
    onRedeem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = title,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Koszt: $cost pkt",
                fontSize = 15.sp,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onRedeem,
                enabled = points >= cost,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD4AF37),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xFFE1E1E1),
                    disabledContentColor = Color(0xFF777777)
                )
            ) {
                Text(
                    text = if (points >= cost) "Odbierz nagrodę" else "Za mało punktów",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}