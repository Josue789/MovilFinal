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
    suspend fun insert(item: DoesOb)

    @Update
    suspend fun update(item: DoesOb)

    @Delete
    suspend fun delete(item: DoesOb)

    @Query("SELECT * from Tareas WHERE id=:idVal ORDER BY title ASC")
    fun getItem(idVal: Int): Flow<DoesOb>

    @Query("SELECT * from Tareas ORDER BY title ASC")
    fun getAllItems(): Flow<List<DoesOb>>
}