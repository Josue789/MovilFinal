package com.example.mynotes.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.mynotes.Notes


@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class InventoryNotesDatabase : RoomDatabase() {

    abstract fun itemDao(): NotesDao

    companion object {
        @Volatile
        private var Instance: InventoryNotesDatabase? = null

        fun getDatabase(context: Context): InventoryNotesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                databaseBuilder(context, InventoryNotesDatabase::class.java, "item_database")
                    .build().also { Instance = it }
            }
        }
    }
}