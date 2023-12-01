package com.example.mynotes.Data

import kotlinx.coroutines.flow.Flow

class OfflineDoesRepository(private val doesDao: DoesDao): DoesRepository {
    override fun getAllItemsStream(): Flow<List<DoesOb>> = doesDao.getAllItems()

    override fun getItemStream(id: Int): Flow<DoesOb?> = doesDao.getItem(id)

    override suspend fun insertItem(item: DoesOb) = doesDao.insert(item)

    override suspend fun deleteItem(item: DoesOb) = doesDao.delete(item)

    override suspend fun updateItem(item: DoesOb) = doesDao.update(item)

}