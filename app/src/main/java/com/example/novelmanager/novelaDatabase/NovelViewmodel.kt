package com.example.novelmanager.novelaDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.novelmanager.database.entidades.Novel
import kotlinx.coroutines.launch

class NovelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NovelRepository = NovelRepository(application)
    val allNovels: LiveData<List<Novel>> = repository.allNovels

    fun agregarNovela(novel: Novel) {
        repository.insert(novel)
    }

    fun eliminarNovela(novel: Novel) {
        repository.delete(novel)
    }

    fun updateFavoriteStatus(novelId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteStatus(novelId, isFavorite)
        }
    }

    fun setNovels(novelList: List<Novel>) {
        (allNovels as MutableLiveData).value = novelList
    }
}