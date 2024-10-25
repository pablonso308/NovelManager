package com.example.novelmanager

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.novelmanager.database.entidades.Novel

class MainActivity : AppCompatActivity() {

    private lateinit var buttonAddBook: Button
    private lateinit var buttonDeleteBook : Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var novelViewModel: NovelViewModel
    private lateinit var novelAdapter: NovelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddBook = findViewById(R.id.buttonAddBook)
        buttonDeleteBook = findViewById(R.id.buttonDeleteBook)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        novelAdapter = NovelAdapter()
        recyclerView.adapter = novelAdapter

        novelViewModel = ViewModelProvider(this).get(NovelViewModel::class.java)

        novelViewModel.getAllNovels().observe(this, Observer { novels ->
            novelAdapter.setNovels(novels)
        })

        buttonAddBook.setOnClickListener {
            val newNovel = Novel(
                title = "New Title", // Replace with actual input
                author = "New Author", // Replace with actual input
                year = 2023, // Replace with actual input
                synopsis = "New Synopsis" // Replace with actual input
            )
            novelViewModel.agregarNovela(newNovel)
            Toast.makeText(this, "Novel added", Toast.LENGTH_SHORT).show()
        }

        buttonDeleteBook.setOnClickListener {
            val novelToDelete = novelAdapter.getSelectedNovel() // Implement this method to get the selected novel
            if (novelToDelete != null) {
                novelViewModel.eliminarNovela(novelToDelete)
                Toast.makeText(this, "Novel deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No novel selected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}