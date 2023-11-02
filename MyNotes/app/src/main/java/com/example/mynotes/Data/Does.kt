package com.example.mynotes.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Tareas")
data class Does(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: Date,
    val dateEnd: Date,
    val description: String,
)
