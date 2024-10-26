package com.example.novelmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.novelmanager.database.entidades.Novel
import com.example.novelmanager.database.entidades.Review

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddBook: Button
    private lateinit var buttonDeleteBook: Button
    private lateinit var buttonAddReview: Button
    private lateinit var buttonShowReviews: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var novelViewModel: NovelViewModel
    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var novelAdapter: NovelAdapter
    private lateinit var buttonFavoriteBook: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddBook = findViewById(R.id.buttonAddBook)
        buttonDeleteBook = findViewById(R.id.buttonDeleteBook)
        buttonAddReview = findViewById(R.id.buttonAddReview)
        buttonShowReviews = findViewById(R.id.buttonShowReviews)
        recyclerView = findViewById(R.id.recyclerView)
        buttonFavoriteBook = findViewById(R.id.buttonFavoriteBook)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        novelAdapter = NovelAdapter()
        recyclerView.adapter = novelAdapter

        novelViewModel = ViewModelProvider(this).get(NovelViewModel::class.java)
        reviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)

        novelViewModel.fetchAllNovels().observe(this, Observer { novels ->
            novelAdapter.setNovels(novels)
        })

        buttonAddBook.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_novel, null)
            val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
            val editTextAuthor = dialogView.findViewById<EditText>(R.id.editTextAuthor)
            val editTextYear = dialogView.findViewById<EditText>(R.id.editTextYear)
            val editTextSynopsis = dialogView.findViewById<EditText>(R.id.editTextSynopsis)

            AlertDialog.Builder(this)
                .setTitle("Agregar Novela")
                .setView(dialogView)
                .setPositiveButton("Agregar") { _, _ ->
                    val title = editTextTitle.text.toString()
                    val author = editTextAuthor.text.toString()
                    val year = editTextYear.text.toString().toIntOrNull() ?: 0
                    val synopsis = editTextSynopsis.text.toString()

                    val newNovel = Novel(
                        title = title,
                        author = author,
                        year = year,
                        synopsis = synopsis
                    )
                    novelViewModel.agregarNovela(novel = newNovel)
                    Toast.makeText(this, "Novel added", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }

        buttonDeleteBook.setOnClickListener {
            val novelToDelete = novelAdapter.getSelectedNovel()
            if (novelToDelete != null) {
                novelViewModel.eliminarNovela(novelToDelete)
                Toast.makeText(this, "Novel deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
            }
        }

        buttonAddReview.setOnClickListener {
            val selectedNovel = novelAdapter.getSelectedNovel()
            if (selectedNovel != null) {
                val dialogView = LayoutInflater.from(this).inflate(R.layout.add_review, null)
                val editTextReviewer = dialogView.findViewById<EditText>(R.id.editTextReviewer)
                val editTextRating = dialogView.findViewById<EditText>(R.id.editTextRating)
                val editTextComment = dialogView.findViewById<EditText>(R.id.editTextComment)

                AlertDialog.Builder(this)
                    .setTitle("Agregar Reseña")
                    .setView(dialogView)
                    .setPositiveButton("Agregar") { _, _ ->
                        val reviewer = editTextReviewer.text.toString()
                        val rating = editTextRating.text.toString().toIntOrNull() ?: 0
                        val comment = editTextComment.text.toString()

                        val newReview = Review(
                            novelId = selectedNovel.id,
                            reviewer = reviewer,
                            rating = rating,
                            comment = comment
                        )
                        reviewViewModel.addReview(newReview)
                        Toast.makeText(this, "Review added", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancelar", null)
                    .create()
                    .show()
            } else {
                Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
            }
        }

        buttonShowReviews.setOnClickListener {
            val selectedNovel = novelAdapter.getSelectedNovel()
            if (selectedNovel != null) {
                reviewViewModel.getReviewsForNovel(selectedNovel.id).observe(this, Observer { reviews ->
                    val reviewsText = reviews.joinToString("\n") { review ->
                        "Reviewer: ${review.reviewer}\nRating: ${review.rating}\nComment: ${review.comment}\n"
                    }
                    AlertDialog.Builder(this)
                        .setTitle("Reseñas")
                        .setMessage(reviewsText)
                        .setPositiveButton("OK", null)
                        .create()
                        .show()
                })
            } else {
                Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
            }
        }

        buttonFavoriteBook.setOnClickListener {
            val selectedNovel = novelAdapter.getSelectedNovel()
            if (selectedNovel != null) {
                val isFavorite = !selectedNovel.isFavorite
                novelViewModel.updateFavoriteStatus(selectedNovel.id, isFavorite)
                Toast.makeText(this, if (isFavorite) "Novel marked as favorite" else "Novel unmarked as favorite", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
            }
        }

    }
}