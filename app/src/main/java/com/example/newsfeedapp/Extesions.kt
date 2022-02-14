package com.example.newsfeedapp

import com.example.newsfeedapp.model.News
import com.example.testapp.model.QueryResult

// Убрать отсюда
fun getNewsList(qRes: QueryResult) : List<News> = qRes.data?.news ?: listOf<News>()