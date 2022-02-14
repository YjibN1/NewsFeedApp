package com.example.newsfeedapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.model.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(
    itemView: View,
    private val itemClickListener: NewsAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    fun bind(news: News) {
        with(itemView) {
            news.run {
                news_id.text = id
                news_title.text = title
                news_annotation.text = annotation

                //Glide.with(context).load(avatarUrl).into(avatarImageView)

                    // TODO: Слушатель нажатия
//                deleteItemImageView.setOnClickListener {
//                    itemClickListener.onItemClicked(id = id)
//                }
            }
        }
    }
}