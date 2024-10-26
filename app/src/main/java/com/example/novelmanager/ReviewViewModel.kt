package com.example.novelmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.novelmanager.database.ReviewDatabase
import com.example.novelmanager.database.entidades.Review
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {
    private val reviewDao = ReviewDatabase.getInstance(application).reviewDao()

    fun addReview(review: Review) {
        viewModelScope.launch {
            reviewDao.insert(review)
        }
    }

    fun getReviewsForNovel(novelId: Int): LiveData<List<Review>> {
        return reviewDao.getReviewsForNovel(novelId)
    }
}