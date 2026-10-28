package com.example.data

import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val historyDao: HistoryDao) {
    val allHistory: Flow<List<HistoryEntity>> = historyDao.getAllHistory()

    suspend fun getById(id: Long): HistoryEntity? = historyDao.getHistoryById(id)

    suspend fun insert(history: HistoryEntity): Long = historyDao.insertHistory(history)

    suspend fun delete(id: Long) = historyDao.deleteHistoryById(id)

    suspend fun clearAll() = historyDao.clearAllHistory()
}
