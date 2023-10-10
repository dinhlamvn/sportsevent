package com.inspius.sportsevent.service

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.inspius.sportsevent.R
import com.inspius.sportsevent.common.AppExtras
import kotlin.random.Random

class UpcomingMatchesReminderService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Dinh", "upcoming reminder service started")

        val notification =
            buildNotification(intent?.getStringExtra(AppExtras.EXTRA_NOTIFICATION_MESSAGE))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(this).notify(Random.nextInt(), notification)
            }
        } else {
            NotificationManagerCompat.from(this).notify(Random.nextInt(), notification)
        }

        return START_NOT_STICKY
    }

    private fun buildNotification(message: String?): Notification {
        return NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.matches_upcoming_notification_title))
            .setContentText(message).setAutoCancel(true).setContentIntent(
                PendingIntent.getActivity(
                    this,
                    1234,
                    packageManager.getLaunchIntentForPackage(packageName),
                    PendingIntent.FLAG_IMMUTABLE
                )
            ).build()
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}