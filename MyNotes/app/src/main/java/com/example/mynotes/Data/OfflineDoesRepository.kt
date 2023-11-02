package com.example.mynotes.Data

import kotlinx.coroutines.flow.Flow

abstract class OfflineDoesRepository(private val DoesDao: DoesDao): DoesRepository {
    fun getAllDoessStream(): Flow<List<Does>> = DoesDao.getAllItems()

    fun getDoesStream(id: Int): Flow<Does?> = DoesDao.getItem(id)

    suspend fun insertDoes(Does: Does) = DoesDao.insert(Does)

    suspend fun deleteDoes(Does: Does) = DoesDao.delete(Does)

    suspend fun updateDoes(Does: Does) = DoesDao.update(Does)

}