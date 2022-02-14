package com.example.newsfeedapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.model.ShortNews

class NewsAdapter(private val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<NewsViewHolder>() {

    private val newsList = mutableListOf<ShortNews>()

    @SuppressLint("NotifyDataSetChanged")
    fun addNews(news: ShortNews) {
        newsList.add(news)
        notifyDataSetChanged()
    }

    // Обновляет данные
    fun setNews(news: List<ShortNews>){
        newsList.clear()
        newsList.addAll(news)
        notifyDataSetChanged()
    }


    // Вызывается для создания ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView, itemClickListener)
    }

    override fun getItemCount() = newsList.size

    // Заполняет данные ячейки
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    interface OnItemClickListener {
        fun onItemClicked(id: String)
    }
}