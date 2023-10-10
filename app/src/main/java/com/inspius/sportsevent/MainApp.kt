package com.inspius.sportsevent

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.inspius.sportsevent.imageloader.GlideImageLoader
import com.inspius.sportsevent.imageloader.ImageLoader

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ImageLoader.loader = GlideImageLoader()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannelCompat.Builder(
                getString(R.string.default_notification_channel_id),
                NotificationManager.IMPORTANCE_DEFAULT
            ).setName("Main Channel").setDescription("This channel to notify to user").build()

            NotificationManagerCompat.from(this).createNotificationChannelsCompat(
                listOf(notificationChannel)
            )
        }
    }
}