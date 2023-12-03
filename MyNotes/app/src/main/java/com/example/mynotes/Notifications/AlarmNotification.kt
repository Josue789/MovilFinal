package com.example.mynotes.Notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.core.app.NotificationCompat
import com.example.mynotes.R
import kotlin.random.Random


class AlarmNotification(): BroadcastReceiver() {
    companion object {
        var NOTIFICACION_ID = Random.nextInt(1,10000)
    }


    override fun onReceive(context: Context, intent: Intent) {
        createNotification(context,intent)
    }

    private fun createNotification(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val time = intent.getStringExtra("fecha")

        val notificacion = NotificationCompat.Builder(context, "CanalTareas")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Tarea pendiente")
            .setContentText("La tarea $title caduca el dia $time ")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("La tarea $title caduca el dia $time")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(NOTIFICACION_ID++, notificacion)
    }
}