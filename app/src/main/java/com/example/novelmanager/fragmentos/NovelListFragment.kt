package com.example.novelmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.novelmanager.novelaDatabase.NovelAdapter
import com.example.novelmanager.novelaDatabase.NovelViewModel

class NovelListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var novelAdapter: NovelAdapter
    private lateinit var novelViewModel: NovelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_novel_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        novelAdapter = NovelAdapter()
        recyclerView.adapter = novelAdapter

        novelViewModel = ViewModelProvider(requireActivity()).get(NovelViewModel::class.java)
        novelViewModel.allNovels.observe(viewLifecycleOwner, Observer { novels ->
            novelAdapter.setNovels(novels)
        })

        return view
    }
}