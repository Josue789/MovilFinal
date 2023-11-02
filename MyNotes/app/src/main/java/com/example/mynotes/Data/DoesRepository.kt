package com.example.mynotes.Data
import kotlinx.coroutines.flow.Flow

interface DoesRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Does>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Does?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Does)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Does)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: Does)
}