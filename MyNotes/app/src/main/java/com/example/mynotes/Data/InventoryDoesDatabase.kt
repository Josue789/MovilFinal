package com.example.mynotes.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Does::class], version = 1, exportSchema = false)
abstract class InventoryDoesDatabase : RoomDatabase() {

    abstract fun itemDao(): NotesDao

    companion object {
        @Volatile
        private var Instance: InventoryDoesDatabase? = null

        fun getDatabase(context: Context): InventoryDoesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                databaseBuilder(context, InventoryDoesDatabase::class.java, "item_database")
                    .build().also { Instance = it }
            }
        }
    }
}

