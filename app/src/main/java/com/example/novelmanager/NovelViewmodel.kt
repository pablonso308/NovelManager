package com.example.novelmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.novelmanager.database.entidades.Novel

class NovelViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NovelRepository = NovelRepository(application)
    val allNovels: LiveData<List<Novel>> = repository.getAllNovels()

    fun getAllNovels(): LiveData<List<Novel>> {
        return allNovels
    }

    fun agregarNovela(novel: Novel) {
        repository.insert(novel)
    }

    fun eliminarNovela(novel: Novel) {
        repository.delete(novel)
    }
}