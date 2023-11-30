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
    suspend fun insert(item: NotesOb)

    @Update
    suspend fun update(item: NotesOb)

    @Delete
    suspend fun delete(item: NotesOb)

    @Query("SELECT * from Notas WHERE id=:idVal ORDER BY title ASC")
    fun getItem(idVal: Int): Flow<NotesOb>

    @Query("SELECT * from Notas ORDER BY title ASC")
    fun getAllItems(): Flow<List<NotesOb>>
}