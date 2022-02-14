package com.example.newsfeedapp.db

import com.example.newsfeedapp.model.News

class NewsRepositoryImpl(private val newsDao: NewsDao) : NewsRepository {
    override suspend fun insert(news: News) {
        newsDao.insert(news)
    }

    override suspend fun getAllNews() = newsDao.getAllPersons()

    // TODO: добавить алиас для String
    override suspend fun deleteById(id: String) {
        newsDao.deleteById(id)
    }
}