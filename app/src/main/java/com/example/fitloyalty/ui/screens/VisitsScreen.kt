package com.example.fitloyalty.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.data.local.PointsDataStore

@Composable
fun VisitsScreen() {

    val context = LocalContext.current

    val pointsDataStore = remember {
        PointsDataStore(context)
    }

    val visits by pointsDataStore.visitsFlow.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Historia wizyt",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Liczba zapisanych wizyt: ${visits.size}",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (visits.isEmpty()) {
            Text("Brak zapisanych wizyt.")
        } else {
            LazyColumn {
                items(visits) { visit ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = visit,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("Status: zapisano lokalnie")
                        }
                    }
                }
            }
        }
    }
}