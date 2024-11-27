package com.example.novelmanager.novelaDatabase

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.novelmanager.database.NovelDatabase
import com.example.novelmanager.database.entidades.Novel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NovelRepository(application: Application) {

    private val novelDao: NovelDao
    val allNovels: LiveData<List<Novel>>
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        val database = NovelDatabase.getInstance(application)
        novelDao = database.novelDao()
        allNovels = novelDao.getAllNovels()
    }

    fun insert(novel: Novel) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                novelDao.insert(novel)
            }
        }
    }

    fun delete(novel: Novel) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                novelDao.delete(novel)
            }
        }
    }

    fun fetchAllNovels(): LiveData<List<Novel>> {
        return allNovels
    }

    suspend fun updateFavoriteStatus(novelId: Int, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            novelDao.updateFavoriteStatus(novelId, isFavorite)
        }
    }

}