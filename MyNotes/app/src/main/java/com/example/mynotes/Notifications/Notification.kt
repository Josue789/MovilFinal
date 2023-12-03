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
fun workFirstAlarm(
    context: Context,
    title: String,
    longDesc: String,
    fchEnd: String,
    hora: Int,
    minutos: Int
){
    val intent = Intent(context, AlarmNotification::class.java)
        .putExtra("title", title)
        .putExtra("desc", longDesc)
        .putExtra("hora",hora)
        .putExtra("minutos",minutos)
        .putExtra("fecha",fchEnd)

    val tiempo: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY,hora)
        set(Calendar.MINUTE,minutos)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID++,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        tiempo.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ScheduleExactAlarm")
fun workAlarm(
    context: Context,
    title: String,
    longDesc: String,
    fchEnd: String,
    hora: Int,
    minutos: Int
){
    val intent = Intent(context, AlarmNotification::class.java)
        .putExtra("title", title)
        .putExtra("desc", longDesc)
        .putExtra("hora",hora)
        .putExtra("minutos",minutos)
        .putExtra("fecha",fchEnd)

    val tiempo: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY,hora)
        set(Calendar.MINUTE,minutos)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID++,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        tiempo.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}