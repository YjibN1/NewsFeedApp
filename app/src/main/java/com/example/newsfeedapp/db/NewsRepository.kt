package com.example.newsfeedapp.db

import com.example.newsfeedapp.model.News

interface NewsRepository {
    suspend fun insert(news: News)

    suspend fun getAllNews(): List<News>

    // TODO: Добавить алиас для String - NewsID
    suspend fun deleteById(id: String)
}