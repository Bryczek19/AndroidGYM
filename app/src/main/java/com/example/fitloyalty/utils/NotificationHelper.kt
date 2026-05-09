package com.example.fitloyalty.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitloyalty.R

class NotificationHelper(
    private val context: Context
) {
    private val channelId = "fitloyalty_notifications"

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "FitLoyalty Powiadomienia",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Powiadomienia o punktach, wizytach i nagrodach"
                enableVibration(true)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showVisitNotification(points: Int) {
        showNotification(
            title = "Dodano wizytę",
            message = "Zdobywasz +$points pkt w FitLoyalty!",
            notificationId = System.currentTimeMillis().toInt()
        )
    }

    fun showRewardNotification(rewardName: String) {
        showNotification(
            title = "Odebrano nagrodę 🎉",
            message = "Nagroda: $rewardName została dodana do historii.",
            notificationId = System.currentTimeMillis().toInt()
        )
    }

    fun showLevelNotification(levelName: String) {
        showNotification(
            title = "Nowy poziom!",
            message = "Gratulacje! Osiągasz poziom $levelName.",
            notificationId = System.currentTimeMillis().toInt()
        )
    }

    private fun showNotification(
        title: String,
        message: String,
        notificationId: Int
    ) {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(notificationId, notification)
    }
}