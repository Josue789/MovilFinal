package com.example.mynotes.Data
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<NotesOb>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<NotesOb?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: NotesOb)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: NotesOb)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: NotesOb)
}