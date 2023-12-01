package com.example.mynotes.Notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.mynotes.R


class AlarmNotification(): BroadcastReceiver() {
    companion object {
        const val NOTIFICACION_ID = 5
    }

    override fun onReceive(context: Context, intent: Intent) {
        createNotification(context,intent)
    }

    private fun createNotification(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val time = intent.getStringExtra("time")

        val notificacion = NotificationCompat.Builder(context, "CanalTareas")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Titulo tarea")
            .setContentText("La tarea $title caduca el dia $time ")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("La tarea $title caduca el dia $time ")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(NOTIFICACION_ID, notificacion)
    }
}