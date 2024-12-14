package com.example.novelmanager

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.novelmanager.SQLlite.SQLDao
import com.example.novelmanager.database.entidades.Novel
import com.example.novelmanager.novelaDatabase.NovelAdapter
import com.example.novelmanager.novelaDatabase.NovelViewModel
import com.example.novelmanager.optimizacion.BatteryJobService
import com.example.novelmanager.optimizacion.NetworkJobService


class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddBook: Button
    private lateinit var buttonDeleteBook: Button
    private lateinit var buttonAddReview: Button
    private lateinit var buttonShowReviews: Button
    private lateinit var buttonFavoriteBook: Button
    private lateinit var buttonSettings: Button
    private lateinit var buttonLocateMe: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var novelAdapter: NovelAdapter
    private lateinit var sqlDao: SQLDao
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var novelViewModel: NovelViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences =
            getSharedPreferences("com.example.novelmanager_preferences", MODE_PRIVATE)
        applyUserSettings()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddBook = findViewById(R.id.buttonAddBook)
        buttonDeleteBook = findViewById(R.id.buttonDeleteBook)
        buttonAddReview = findViewById(R.id.buttonAddReview)
        buttonShowReviews = findViewById(R.id.buttonShowReviews)
        buttonFavoriteBook = findViewById(R.id.buttonFavoriteBook)
        buttonSettings = findViewById(R.id.buttonSettings)
        buttonLocateMe = findViewById(R.id.buttonLocateMe)
        recyclerView = findViewById(R.id.recyclerView)
        sqlDao = SQLDao(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        novelAdapter = NovelAdapter()
        recyclerView.adapter = novelAdapter

        novelViewModel = ViewModelProvider(this).get(NovelViewModel::class.java)
        novelViewModel.allNovels.observe(this, Observer { novels ->
            novelAdapter.setNovels(novels)
        })

        loadNovelsFromDatabase()

        buttonAddBook.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_novel, null)
            val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
            val editTextAuthor = dialogView.findViewById<EditText>(R.id.editTextAuthor)
            val editTextYear = dialogView.findViewById<EditText>(R.id.editTextYear)
            val editTextSynopsis = dialogView.findViewById<EditText>(R.id.editTextSynopsis)
            val editTextLatitude = dialogView.findViewById<EditText>(R.id.editTextLatitude)
            val editTextLongitude = dialogView.findViewById<EditText>(R.id.editTextLongitude)

            AlertDialog.Builder(this)
                .setTitle("Agregar Novela")
                .setView(dialogView)
                .setPositiveButton("Agregar") { _, _ ->
                    val title = editTextTitle.text.toString()
                    val author = editTextAuthor.text.toString()
                    val year = editTextYear.text.toString().toIntOrNull() ?: 0
                    val synopsis = editTextSynopsis.text.toString()
                    val latitude = editTextLatitude.text.toString().toDoubleOrNull()
                    val longitude = editTextLongitude.text.toString().toDoubleOrNull()

                    // Llama a insertNovel con latitude y longitude
                    val newNovelId = sqlDao.insertNovel(title, author, year, synopsis, false, latitude, longitude)
                    if (newNovelId != -1L) {
                        Toast.makeText(this, "Novel added with ID: $newNovelId", Toast.LENGTH_SHORT).show()
                        loadNovelsFromDatabase()
                    } else {
                        Toast.makeText(this, "Error adding novel", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }


        buttonDeleteBook.setOnClickListener {
            val novelToDelete = novelAdapter.getSelectedNovel()
            if (novelToDelete != null) {
                val rowsDeleted = sqlDao.deleteNovel(novelToDelete.id.toLong())
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Novel deleted", Toast.LENGTH_SHORT).show()
                    loadNovelsFromDatabase()
                }
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

                        val newReviewId =
                            sqlDao.insertReview(selectedNovel.id, reviewer, rating, comment)
                        if (newReviewId != -1L) {
                            Toast.makeText(this, "Review added", Toast.LENGTH_SHORT).show()
                        }
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
                val cursor = sqlDao.getReviewsForNovel(selectedNovel.id)
                val reviewsText = StringBuilder()
                while (cursor.moveToNext()) {
                    val reviewer = cursor.getString(cursor.getColumnIndexOrThrow("reviewer"))
                    val rating = cursor.getInt(cursor.getColumnIndexOrThrow("rating"))
                    val comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"))
                    reviewsText.append("Reviewer: $reviewer\nRating: $rating\nComment: $comment\n\n")
                }
                cursor.close()
                AlertDialog.Builder(this)
                    .setTitle("Reseñas")
                    .setMessage(reviewsText.toString())
                    .setPositiveButton("OK", null)
                    .create()
                    .show()
            } else {
                Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
            }
        }

    buttonFavoriteBook.setOnClickListener {
    val selectedNovel = novelAdapter.getSelectedNovel()
    if (selectedNovel != null) {
        val isFavorite = !selectedNovel.isFavorite
        val updatedNovel = Novel(
            id = selectedNovel.id,
            title = selectedNovel.title,
            author = selectedNovel.author,
            year = selectedNovel.year,
            synopsis = selectedNovel.synopsis,
            latitude = selectedNovel.latitude,
            longitude = selectedNovel.longitude,
            isFavorite = isFavorite
        )
        sqlDao.updateNovel(updatedNovel)
        Toast.makeText(
            this,
            if (isFavorite) "Novel marked as favorite" else "Novel unmarked as favorite",
            Toast.LENGTH_SHORT
        ).show()

        loadNovelsFromDatabase()
    } else {
        Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
                }
        }


        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        buttonLocateMe.setOnClickListener {
            val intent = Intent(this, LocateMeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadNovelsFromDatabase() {
        val cursor = sqlDao.getAllNovels()
        val novels = mutableListOf<Novel>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
            val synopsis = cursor.getString(cursor.getColumnIndexOrThrow("synopsis"))
            val latitude = if (cursor.isNull(cursor.getColumnIndexOrThrow("latitude"))) null else cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"))
            val longitude = if (cursor.isNull(cursor.getColumnIndexOrThrow("longitude"))) null else cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"))
            val isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow("isFavorite")) == 1

            novels.add(Novel(id, title, author, year, synopsis, isFavorite, latitude, longitude))
        }
        cursor.close()
        novelAdapter.setNovels(novels)
    }


    private fun applyUserSettings() {
        val darkMode = sharedPreferences.getBoolean("dark_mode", false)
        if (darkMode) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    private fun optimizeNetworkUsage() {
        // Reducir la frecuencia de las actualizaciones en segundo plano
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(1, ComponentName(this, NetworkJobService::class.java))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPeriodic(15 * 60 * 1000) // 15 minutos
            .build()
        jobScheduler.schedule(jobInfo)
    }

    private fun optimizeBatteryUsage() {
        // Usar JobScheduler para tareas en segundo plano
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(2, ComponentName(this, BatteryJobService::class.java))
            .setRequiresCharging(true)
            .setPeriodic(30 * 60 * 1000) // 30 minutos
            .build()
        jobScheduler.schedule(jobInfo)
    }
}