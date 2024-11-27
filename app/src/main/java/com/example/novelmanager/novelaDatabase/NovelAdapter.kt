package com.example.novelmanager.novelaDatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.novelmanager.R
import com.example.novelmanager.database.entidades.Novel

class NovelAdapter : RecyclerView.Adapter<NovelAdapter.NovelViewHolder>() {

    private var novels: List<Novel> = listOf()
    private var selectedNovel: Novel? = null

    fun setNovels(novels: List<Novel>) {
        this.novels = novels
        notifyDataSetChanged()
    }

    fun getSelectedNovel(): Novel? {
        return selectedNovel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_novel, parent, false)
        return NovelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val currentNovel = novels[position]
        holder.bind(currentNovel)
        holder.itemView.setOnClickListener {
            selectedNovel = currentNovel
            notifyDataSetChanged()
        }
        holder.itemView.isSelected = selectedNovel == currentNovel
        holder.showDetails(selectedNovel == currentNovel)
    }

    override fun getItemCount(): Int {
        return novels.size
    }

    class NovelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        private val textViewYear: TextView = itemView.findViewById(R.id.textViewYear)
        private val textViewSynopsis: TextView = itemView.findViewById(R.id.textViewSynopsis)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.imageViewFavorite)

        fun bind(novel: Novel) {
            textViewTitle.text = novel.title
            textViewAuthor.text = novel.author
            textViewYear.text = novel.year.toString()
            textViewSynopsis.text = novel.synopsis
            favoriteImageView.visibility = if (novel.isFavorite) View.VISIBLE else View.GONE
        }

        fun showDetails(show: Boolean) {
            textViewYear.visibility = if (show) View.VISIBLE else View.GONE
            textViewSynopsis.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}