package com.example.mynotes.Notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mynotes.Notifications.AlarmNotification.Companion.NOTIFICACION_ID
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ScheduleExactAlarm")
fun workAlarm(
    context: Context,
    title: String,
    longDesc: String,
    fchEnd: String,
    time: Long = 10000
){
    val intent = Intent(context, AlarmNotification::class.java)
        .putExtra("title", title)
        .putExtra("desc", longDesc)
        .putExtra("time",fchEnd)


    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        Calendar.getInstance().timeInMillis + 5000,
        pendingIntent
    )
}