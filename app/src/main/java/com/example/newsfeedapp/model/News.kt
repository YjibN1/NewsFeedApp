package com.example.testapp.model

import com.example.newsfeedapp.model.ShortNews

data class News(
    val id: String? = null,
    val title: String?= null,
    val img: String? = null,
    val local_img: String? = null,
    val news_date: String? = null,
    val annotation: String? = null,
    val id_resource: String? = null,
    val news_date_uts: String? = null,
    val mobile_url: String? = null
)