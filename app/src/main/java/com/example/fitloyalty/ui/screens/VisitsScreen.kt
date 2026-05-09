package com.example.fitloyalty.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun VisitsScreen(pointsViewModel: PointsViewModel) {

    val visits by pointsViewModel.visits.collectAsState()

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
                            text = "Historia wizyt",
                            fontSize = 33.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Twoja aktywność w klubie",
                            fontSize = 15.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Text(
                        text = "${visits.size} zapisanych wizyt",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            VisitSummaryCard(
                visitsCount = visits.size
            )
        }

        if (visits.isEmpty()) {
            item {
                EmptyVisitsCard()
            }
        } else {
            itemsIndexed(visits) { index, visit ->
                VisitCard(
                    number = visits.size - index,
                    visit = visit
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun VisitSummaryCard(
    visitsCount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Podsumowanie",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Liczba wizyt: $visitsCount",
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )

            Text(
                text = "Punkty z wizyt: ${visitsCount * 10} pkt",
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )

            Text(
                text = "Status danych: lokalnie + Firebase",
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )
        }
    }
}

@Composable
fun EmptyVisitsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Brak zapisanych wizyt",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Kliknij przycisk na ekranie głównym, aby dodać pierwszą wizytę i zdobyć punkty.",
                fontSize = 15.sp,
                color = Color(0xFF777777)
            )
        }
    }
}

@Composable
fun VisitCard(
    number: Int,
    visit: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Wizyta #$number",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = visit,
                fontSize = 15.sp,
                color = Color(0xFF555555),
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color(0xFFE9F7E8),
                        RoundedCornerShape(100.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "Zapisano lokalnie i online",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1D7A32)
                )
            }
        }
    }
}