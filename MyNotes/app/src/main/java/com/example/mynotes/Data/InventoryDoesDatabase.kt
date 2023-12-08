package com.example.mynotes.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DoesOb::class], version = 2, exportSchema = false)
abstract class InventoryDoesDatabase : RoomDatabase() {

    abstract fun itemDao(): DoesDao

    companion object {
        @Volatile
        private var Instance: InventoryDoesDatabase? = null

        fun getDatabase(context: Context): InventoryDoesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDoesDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

