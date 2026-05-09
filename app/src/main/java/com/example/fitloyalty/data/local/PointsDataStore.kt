package com.example.fitloyalty.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Context.dataStore by preferencesDataStore(name = "fitloyalty_settings")

class PointsDataStore(
    private val context: Context
) {
    companion object {
        private val POINTS_KEY = intPreferencesKey("points")
        private val VISITS_KEY = stringPreferencesKey("visits")
        private val REWARDS_KEY = stringPreferencesKey("rewards")
        private val DARK_MODE_KEY = intPreferencesKey("dark_mode")
    }

    val pointsFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[POINTS_KEY] ?: 0
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        (preferences[DARK_MODE_KEY] ?: 0) == 1
    }

    val visitsFlow: Flow<List<String>> = context.dataStore.data.map { preferences ->
        val visitsText = preferences[VISITS_KEY] ?: ""
        if (visitsText.isBlank()) emptyList()
        else visitsText.split(";;").reversed()
    }

    val rewardsFlow: Flow<List<String>> = context.dataStore.data.map { preferences ->
        val rewardsText = preferences[REWARDS_KEY] ?: ""
        if (rewardsText.isBlank()) emptyList()
        else rewardsText.split(";;").reversed()
    }

    suspend fun savePoints(points: Int) {
        context.dataStore.edit { preferences ->
            preferences[POINTS_KEY] = points
        }
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = if (enabled) 1 else 0
        }
    }

    suspend fun addVisit(userName: String = "Użytkownik") {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())

        val entryTime = Date()
        val trainingMinutes = listOf(45, 60, 75, 90, 105).random()
        val exitTime = Date(entryTime.time + trainingMinutes * 60 * 1000)

        val entryText = dateFormat.format(entryTime)
        val exitText = dateFormat.format(exitTime)

        val newVisit =
            "Użytkownik: $userName\n" +
                    "Wejście: $entryText\n" +
                    "Wyjście: $exitText\n" +
                    "Czas treningu: $trainingMinutes min\n" +
                    "Punkty: +10 pkt"

        context.dataStore.edit { preferences ->
            val currentPoints = preferences[POINTS_KEY] ?: 0
            val currentVisits = preferences[VISITS_KEY] ?: ""

            preferences[POINTS_KEY] = currentPoints + 10
            preferences[VISITS_KEY] =
                if (currentVisits.isBlank()) newVisit else "$currentVisits;;$newVisit"
        }
    }

    suspend fun redeemReward(rewardName: String, cost: Int) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        val newReward = "$currentDate — Odebrano: $rewardName — -$cost pkt"

        context.dataStore.edit { preferences ->
            val currentPoints = preferences[POINTS_KEY] ?: 0
            val currentRewards = preferences[REWARDS_KEY] ?: ""

            if (currentPoints >= cost) {
                preferences[POINTS_KEY] = currentPoints - cost
                preferences[REWARDS_KEY] =
                    if (currentRewards.isBlank()) newReward else "$currentRewards;;$newReward"
            }
        }
    }
}