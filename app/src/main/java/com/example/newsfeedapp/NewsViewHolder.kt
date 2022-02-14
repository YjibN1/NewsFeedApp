package com.example.newsfeedapp

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.model.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    val cardView: CardView = itemView.findViewById<View>(R.id.cardView) as CardView

    fun bind(news: News) {
        with(itemView) {
            news.run {
                news_id.text = id
                news_title.text = title
                news_annotation.text = annotation

            }
        }
    }
}