package com.example.mynotes.Data

import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao): NotesRepository {
    override fun getAllItemsStream(): Flow<List<NotesOb>>  = notesDao.getAllItems()

    override fun getItemStream(id: Int): Flow<NotesOb?> = notesDao.getItem(id)

    override suspend fun insertItem(item: NotesOb) = notesDao.insert(item)

    override suspend fun deleteItem(item: NotesOb) = notesDao.delete(item)

    override suspend fun updateItem(item: NotesOb) = notesDao.update(item)

}