package com.example.novelmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NovelDetailFragment : Fragment() {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewAuthor: TextView
    private lateinit var textViewYear: TextView
    private lateinit var textViewSynopsis: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_novel_detail, container, false)

        textViewTitle = view.findViewById(R.id.textViewTitle)
        textViewAuthor = view.findViewById(R.id.textViewAuthor)
        textViewYear = view.findViewById(R.id.textViewYear)
        textViewSynopsis = view.findViewById(R.id.textViewSynopsis)

        // Obtener datos de la novela seleccionada y mostrarlos
        val args = arguments
        if (args != null) {
            textViewTitle.text = args.getString("title")
            textViewAuthor.text = args.getString("author")
            textViewYear.text = args.getInt("year").toString()
            textViewSynopsis.text = args.getString("synopsis")
        }

        return view
    }
}