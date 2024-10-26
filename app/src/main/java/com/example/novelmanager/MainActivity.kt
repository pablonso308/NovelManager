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

        novelViewModel.fetchAllNovels().observe(this, Observer { novels ->
            novelAdapter.setNovels(novels)
        })

        // Dentro del método onCreate
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
                    novelViewModel.agregarNovela(newNovel)
                    Toast.makeText(this, "Novel added", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
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