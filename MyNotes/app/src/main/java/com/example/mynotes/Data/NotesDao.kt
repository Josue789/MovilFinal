package com.example.mynotes.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Notes)

    @Update
    suspend fun update(item: Notes)

    @Delete
    suspend fun delete(item: Notes)

    @Query("SELECT * from notas WHERE id = :id")
    fun getItem(id: Int): Flow<Notes>

    @Query("SELECT * from notas ORDER BY title ASC")
    fun getAllItems(): Flow<List<Notes>>
}