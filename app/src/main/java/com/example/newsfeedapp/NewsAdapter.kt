package com.example.newsfeedapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.model.News

class NewsAdapter(private val onClickListener: MyOnClickListener):
    RecyclerView.Adapter<NewsViewHolder>() {

    private val newsList = mutableListOf<News>()

    @SuppressLint("NotifyDataSetChanged")
    fun addNews(news: News) {
        newsList.add(news)
        notifyDataSetChanged()
    }

    // Обновляет данные
    fun setNews(news: List<News>){
        newsList.clear()
        newsList.addAll(news)
        // TODO: менять конкретные позиции с помощью notifyItemChanged(position), а не весь список
        notifyDataSetChanged()
    }



    // Вызывается для создания ячейки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun getItemCount() = newsList.size

    // Заполняет данные ячейки
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])

        holder.tagTV.setOnClickListener {
            onClickListener.onClicked("Информация в ячейку")
        }
    }
}