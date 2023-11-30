package com.example.mynotes.Data

import kotlinx.coroutines.flow.Flow

class OfflineDoesRepository(private val DoesDao: DoesDao): DoesRepository {

    suspend fun updateDoes(item: DoesOb) = DoesDao.update(item)
    override fun getAllItemsStream(): Flow<List<DoesOb>> = DoesDao.getAllItems()

    override fun getItemStream(id: Int): Flow<DoesOb?> = DoesDao.getItem(id)

    override suspend fun insertItem(item: DoesOb) = DoesDao.insert(item)

    override suspend fun deleteItem(item: DoesOb) = DoesDao.delete(item)

    override suspend fun updateItem(item: DoesOb) = DoesDao.update(item)

}