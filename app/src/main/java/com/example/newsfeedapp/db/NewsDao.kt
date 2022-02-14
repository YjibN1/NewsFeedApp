package com.example.newsfeedapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsfeedapp.model.News

@Dao
interface NewsDao {
    // Сохраняем данные
    @Insert
    suspend fun insert(news: News)

    // Извлечение id новостей
    @Query("SELECT ${News.ID} FROM ${News.TABLE_NAME}")
    suspend fun getAllNewsId(): List<String>

    // Извлечение новостей
    @Query("SELECT * FROM ${News.TABLE_NAME}")
    suspend fun getAllNews(): List<News>


    @Query("DELETE FROM ${News.TABLE_NAME} WHERE ${News.ID} = :id")
    suspend fun deleteById(id: String)
}