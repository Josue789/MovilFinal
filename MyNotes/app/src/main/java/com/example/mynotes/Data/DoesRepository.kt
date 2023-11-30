package com.example.mynotes.Data
import kotlinx.coroutines.flow.Flow

interface DoesRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<DoesOb>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<DoesOb?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: DoesOb)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: DoesOb)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: DoesOb)
}