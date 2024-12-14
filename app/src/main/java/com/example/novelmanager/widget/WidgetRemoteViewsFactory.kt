package com.example.novelmanager.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.novelmanager.R
import com.example.novelmanager.SQLlite.SQLDao
import com.example.novelmanager.database.entidades.Novel

class WidgetRemoteViewsFactory(private val context: Context, intent: Intent) : RemoteViewsService.RemoteViewsFactory {

    private var novels: List<Novel> = listOf()

    override fun onCreate() {
        // Initialize data source
    }

override fun onDataSetChanged() {
    val sqlDao = SQLDao(context)
    val cursor: Cursor = sqlDao.getFavoriteNovels()
    val novelsList = mutableListOf<Novel>()
    while (cursor.moveToNext()) {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
        val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
        val author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
        val year = cursor.getInt(cursor.getColumnIndexOrThrow("year"))
        val synopsis = cursor.getString(cursor.getColumnIndexOrThrow("synopsis"))
        val isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow("isFavorite")) == 1
        val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"))
        val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"))

        // Orden correcto
        novelsList.add(Novel(id, title, author, year, synopsis, isFavorite, latitude, longitude))
    }

    cursor.close()
    novels = novelsList
}
    override fun onDestroy() {
        // Clean up resources
    }

    override fun getCount(): Int {
        return novels.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val novel = novels[position]
        val views = RemoteViews(context.packageName, R.layout.widget_list_item)
        views.setTextViewText(R.id.widget_item_title, novel.title)
        views.setTextViewText(R.id.widget_item_author, novel.author)
        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return novels[position].id.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}