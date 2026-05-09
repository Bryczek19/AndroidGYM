package com.example.fitloyalty.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.fitloyalty.data.repository.FirebaseRepository
import com.example.fitloyalty.model.Gym
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@Composable
fun LocationScreen() {

    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var gyms by remember {
        mutableStateOf<List<Gym>>(emptyList())
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var userLocation by remember {
        mutableStateOf<LatLng?>(null)
    }

    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val defaultLocation = LatLng(52.2297, 21.0122)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            defaultLocation,
            6f
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            permissionGranted = granted

            if (granted) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val current = LatLng(
                            location.latitude,
                            location.longitude
                        )

                        userLocation = current

                        cameraPositionState.move(
                            CameraUpdateFactory.newLatLngZoom(
                                current,
                                12f
                            )
                        )
                    }
                }
            }
        }

    LaunchedEffect(Unit) {
        val repository = FirebaseRepository(context)

        gyms = repository.getGyms()
        loading = false

        if (permissionGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val current = LatLng(
                        location.latitude,
                        location.longitude
                    )

                    userLocation = current

                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(
                            current,
                            12f
                        )
                    )
                } else if (gyms.isNotEmpty()) {
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                gyms.first().latitude,
                                gyms.first().longitude
                            ),
                            6f
                        )
                    )
                }
            }
        } else if (gyms.isNotEmpty()) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        gyms.first().latitude,
                        gyms.first().longitude
                    ),
                    6f
                )
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            gyms.forEach { gym ->
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            gym.latitude,
                            gym.longitude
                        )
                    ),
                    title = gym.name,
                    snippet = gym.address
                )
            }

            userLocation?.let { location ->
                Marker(
                    state = MarkerState(position = location),
                    title = "Twoja lokalizacja",
                    snippet = "Aktualna pozycja użytkownika",
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_AZURE
                    )
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .animateContentSize(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFF101010),
                                Color(0xFF1E1E1E),
                                Color(0xFF2C2412)
                            )
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(18.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Mapa klubów",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Google Maps • Firebase • GPS",
                        fontSize = 14.sp,
                        color = Color(0xFFD4AF37)
                    )

                    Text(
                        text = "Kluby: ${gyms.size} • GPS: ${if (userLocation != null) "aktywny" else "brak"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        if (!permissionGranted) {
            Button(
                onClick = {
                    permissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD4AF37),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Włącz lokalizację GPS",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (!loading && gyms.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Dostępne kluby",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Text(
                        text = "Niebieski marker oznacza Twoją aktualną lokalizację.",
                        fontSize = 14.sp,
                        color = Color(0xFF777777)
                    )

                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    gyms.take(3).forEach { gym ->
                        GymMiniRow(
                            name = gym.name,
                            address = gym.address
                        )
                    }
                }
            }
        }

        if (loading) {
            Card(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(min = 220.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .height(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFD4AF37)
                    )

                    Text(
                        text = "Ładowanie mapy...",
                        modifier = Modifier.padding(top = 12.dp),
                        color = Color(0xFF111111),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun GymMiniRow(
    name: String,
    address: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111111)
            )

            Text(
                text = address,
                fontSize = 13.sp,
                color = Color(0xFF777777)
            )
        }

        Box(
            modifier = Modifier
                .background(
                    Color(0xFFE9F7E8),
                    RoundedCornerShape(100.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Aktywny",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D7A32)
            )
        }
    }
}