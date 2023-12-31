package com.example.mynotes.Data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tareas")
data class DoesOb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val dateEnd: String,
    val description: String,
    val uriImages: String,
    val uriVideos: String,
    var uriAudios: String
)
