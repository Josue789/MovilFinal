package com.example.mynotes.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notas")
data class NotesOb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
) {
}
