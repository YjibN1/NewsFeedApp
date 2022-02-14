package com.example.testapp.model

import com.example.newsfeedapp.model.News

data class Data(
    val news: List<News>?,
    val count: String?,
    val error_msg: String?
) {


    override fun toString(): String {
        val strB = StringBuilder()
        strB.append("Models.Data = \r\n")
        news?.forEach {
            strB.append(it.id + " " + it.title + "\r\n")
        }
        strB.append("$count \r\n")
        strB.append("$error_msg \r\n")

        return strB.toString()
    }
}