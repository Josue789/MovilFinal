package com.example.mynotes.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface DoesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Does)

    @Update
    suspend fun update(item: Does)

    @Delete
    suspend fun delete(item: Does)

    @Query("SELECT * from tareas WHERE id = :id")
    fun getItem(id: Int): Flow<Does>

    @Query("SELECT * from tareas ORDER BY title ASC")
    fun getAllItems(): Flow<List<Does>>
}