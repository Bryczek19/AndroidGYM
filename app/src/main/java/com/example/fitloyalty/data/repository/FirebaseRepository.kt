package com.example.fitloyalty.data.repository

import android.content.Context
import com.example.fitloyalty.model.Gym
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirebaseRepository(
    context: Context
) {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val analytics = FirebaseAnalytics.getInstance(context)

    suspend fun signInAnonymouslyIfNeeded(): String {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            return currentUser.uid
        }

        val result = auth.signInAnonymously().await()
        return result.user?.uid ?: "unknown_user"
    }

    suspend fun saveUserProfile(
        points: Int,
        visitsCount: Int,
        rewardsCount: Int
    ) {
        val uid = signInAnonymouslyIfNeeded()

        val profile = hashMapOf(
            "uid" to uid,
            "name" to "Użytkownik",
            "points" to points,
            "visitsCount" to visitsCount,
            "rewardsCount" to rewardsCount,
            "membership" to "Premium",
            "updatedAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(uid)
            .set(profile)
            .await()
    }

    suspend fun saveVisitOnline() {
        val uid = signInAnonymouslyIfNeeded()

        val dateFormat = SimpleDateFormat(
            "dd.MM.yyyy, HH:mm",
            Locale.getDefault()
        )

        val now = Date()
        val trainingMinutes = listOf(45, 60, 75, 90, 105).random()

        val visit = hashMapOf(
            "userId" to uid,
            "date" to dateFormat.format(now),
            "trainingMinutes" to trainingMinutes,
            "points" to 10,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(uid)
            .collection("visits")
            .add(visit)
            .await()

        analytics.logEvent("visit_added", null)
    }

    suspend fun saveRewardOnline(
        rewardName: String,
        cost: Int
    ) {
        val uid = signInAnonymouslyIfNeeded()

        val reward = hashMapOf(
            "userId" to uid,
            "rewardName" to rewardName,
            "cost" to cost,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(uid)
            .collection("rewards")
            .add(reward)
            .await()

        analytics.logEvent("reward_redeemed", null)
    }

    suspend fun getGyms(): List<Gym> {
        return try {
            firestore.collection("gyms")
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(Gym::class.java)
                }
        } catch (e: Exception) {
            emptyList()
        }
    }
}