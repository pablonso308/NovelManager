package com.example.novelmanager.novelaDatabase

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.example.novelmanager.database.entidades.Novel

class NovelLoader(context: Context) : AsyncTaskLoader<List<Novel>>(context) {

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): List<Novel>? {
        // Load data in the background
        return loadNovelsFromDatabase()
    }

    private fun loadNovelsFromDatabase(): List<Novel>? {
        // Replace with actual database query
        return listOf(
            Novel(id = 1, title = "Sample Title 1", author = "Author 1", year = 2021, synopsis = "Synopsis 1"),
            Novel(id = 2, title = "Sample Title 2", author = "Author 2", year = 2022, synopsis = "Synopsis 2")
        )
    }
}