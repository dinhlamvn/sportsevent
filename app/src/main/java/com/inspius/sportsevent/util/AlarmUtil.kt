package com.inspius.sportsevent.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import com.inspius.sportsevent.R
import com.inspius.sportsevent.common.AppExtras
import com.inspius.sportsevent.extensions.asDate
import com.inspius.sportsevent.model.Matches
import com.inspius.sportsevent.service.UpcomingMatchesReminderService
import kotlin.random.Random

object AlarmUtil {

    fun scheduleUpcomingMatchesReminder(context: Context, data: Matches.Data) {
        val alarmTime = data.date.asDate().time

        if (alarmTime <= System.currentTimeMillis()) {
            return
        }

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        val pendingIntent = PendingIntent.getService(
            context,
            Random.nextInt(),
            Intent(
                context, UpcomingMatchesReminderService::class.java
            ).putExtra(AppExtras.EXTRA_NOTIFICATION_MESSAGE, data.description),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent
                )
                Toast.makeText(context, R.string.schedule_reminder_success, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, R.string.grant_permission_warning, Toast.LENGTH_SHORT)
                    .show()
                context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent
            )
            Toast.makeText(context, R.string.schedule_reminder_success, Toast.LENGTH_SHORT).show()
        }
    }
}