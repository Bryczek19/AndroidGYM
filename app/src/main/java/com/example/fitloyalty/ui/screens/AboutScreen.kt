package com.example.fitloyalty.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitloyalty.R

@Composable
fun AboutScreen() {

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
                    .height(170.dp)
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
                            text = "O aplikacji",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "FitLoyalty • projekt MVP",
                            fontSize = 15.sp,
                            color = Color(0xFFD4AF37)
                        )
                    }

                    Text(
                        text = "Lojalnościowa aplikacja dla siłowni",
                        fontSize = 20.sp,
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
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        text = "FitLoyalty",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Nowoczesna aplikacja lojalnościowa dla siłowni. Użytkownik może zbierać punkty za wizyty, odbierać nagrody, przeglądać historię aktywności, korzystać z map klubów oraz monitorować ruch z czujników telefonu.",
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        color = Color(0xFF666666)
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
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        text = "Elementy projektu",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    FeatureRow("Splash screen", "ekran startowy z animacją")
                    FeatureRow("MVVM", "ViewModel + DataStore")
                    FeatureRow("Firebase", "Auth, Firestore, Analytics")
                    FeatureRow("Mapy", "Google Maps + kluby z bazy")
                    FeatureRow("Czujniki", "akcelerometr i aktywność")
                    FeatureRow("Powiadomienia", "wizyty i nagrody")
                    FeatureRow("Orientacja", "układ pionowy i poziomy")
                }
            }
        }

        item {
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
                        text = "Autorzy projektu",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    AuthorRow("Konrad Bryk", "Autor aplikacji")
                    AuthorRow("FitLoyalty Team", "Projekt i koncepcja MVP")
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Projekt wykonany dla",
                        fontSize = 16.sp,
                        color = Color(0xFF777777)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ideis_logo),
                        contentDescription = "IDEIS Logo",
                        modifier = Modifier
                            .size(210.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "IDEIS",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Android Studio • Kotlin • Jetpack Compose",
                        fontSize = 14.sp,
                        color = Color(0xFF777777)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun FeatureRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF111111)
        )

        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF777777)
        )
    }
}

@Composable
fun AuthorRow(
    name: String,
    role: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF5F2EA),
                RoundedCornerShape(18.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = role,
                fontSize = 14.sp,
                color = Color(0xFF777777)
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}