package com.example.newsfeedapp

import com.example.newsfeedapp.model.ShortNews
import com.example.newsfeedapp.model.News
import com.example.testapp.model.QueryResult

fun News.toShortNews(): ShortNews {
    return ShortNews(this.id, this.title, this.annotation)
}

fun getNewsList(qRes: QueryResult) : List<News> = qRes.data?.news ?: listOf<News>()