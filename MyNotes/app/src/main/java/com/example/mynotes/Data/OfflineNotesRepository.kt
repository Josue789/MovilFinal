package com.example.mynotes.Data

import kotlinx.coroutines.flow.Flow

abstract class OfflineNotesRepository(private val NotesDao: NotesDao): NotesRepository {
    fun getAllNotessStream(): Flow<List<Notes>> = NotesDao.getAllItems()

    fun getNotesStream(id: Int): Flow<Notes?> = NotesDao.getItem(id)

    suspend fun insertNotes(Notes: Notes) = NotesDao.insert(Notes)

    suspend fun deleteNotes(Notes: Notes) = NotesDao.delete(Notes)

    suspend fun updateNotes(Notes: Notes) = NotesDao.update(Notes)

}