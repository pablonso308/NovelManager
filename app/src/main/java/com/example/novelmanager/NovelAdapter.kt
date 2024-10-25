package com.example.novelmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.novelmanager.R
import com.example.novelmanager.database.entidades.Novel

class NovelAdapter : RecyclerView.Adapter<NovelAdapter.NovelViewHolder>() {

    private var novels: List<Novel> = emptyList()
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_novel, parent, false)
        return NovelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val currentNovel = novels[position]
        holder.textViewTitle.text = currentNovel.title
        holder.textViewAuthor.text = currentNovel.author
    }

    override fun getItemCount(): Int {
        return novels.size
    }

    fun setNovels(novels: List<Novel>) {
        this.novels = novels
        notifyDataSetChanged()
    }
    fun getSelectedNovel(): Novel? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            novels[selectedPosition]
        } else {
            null
        }
    }

    inner class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
    }
}
