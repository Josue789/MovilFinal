package com.example.mynotes.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [NotesOb::class], version = 1, exportSchema = false)
abstract class InventoryNotesDatabase : RoomDatabase() {

    abstract fun itemDao(): NotesDao
    companion object {
        @Volatile
        public var Instance: InventoryNotesDatabase? = null
        fun getDatabase(context: Context): InventoryNotesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryNotesDatabase::class.java, "notasDatabase")
                    .allowMainThreadQueries() 
                    .build()
                    .also { Instance = it }
            }
        }

    }
}