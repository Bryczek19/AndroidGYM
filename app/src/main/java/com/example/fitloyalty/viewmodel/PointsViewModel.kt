package com.example.fitloyalty.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitloyalty.data.local.PointsDataStore
import com.example.fitloyalty.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PointsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = PointsDataStore(application.applicationContext)
    private val firebaseRepository = FirebaseRepository(application.applicationContext)

    val points = dataStore.pointsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    val visits = dataStore.visitsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val rewards = dataStore.rewardsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val darkMode = dataStore.darkModeFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val notifications = dataStore.notificationsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

    init {
        viewModelScope.launch {
            firebaseRepository.signInAnonymouslyIfNeeded()
        }
    }

    fun addVisit() {
        viewModelScope.launch {
            dataStore.addVisit()

            firebaseRepository.saveVisitOnline()

            firebaseRepository.saveUserProfile(
                points = points.value + 10,
                visitsCount = visits.value.size + 1,
                rewardsCount = rewards.value.size
            )
        }
    }

    fun redeemReward(name: String, cost: Int) {
        viewModelScope.launch {
            if (points.value >= cost) {
                dataStore.redeemReward(name, cost)

                firebaseRepository.saveRewardOnline(name, cost)

                firebaseRepository.saveUserProfile(
                    points = points.value - cost,
                    visitsCount = visits.value.size,
                    rewardsCount = rewards.value.size + 1
                )
            }
        }
    }

    fun saveDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.saveDarkMode(enabled)
        }
    }

    fun saveNotifications(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.saveNotifications(enabled)
        }
    }

    fun resetAllData() {
        viewModelScope.launch {
            dataStore.resetAllData()

            firebaseRepository.saveUserProfile(
                points = 0,
                visitsCount = 0,
                rewardsCount = 0
            )
        }
    }

    fun resetSettings() {
        viewModelScope.launch {
            dataStore.resetSettings()
        }
    }
}