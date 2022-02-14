package com.example.newsfeedapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeedapp.model.News
import com.example.testapp.model.Data
import com.example.testapp.model.QueryResult
import getQueryResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView(this)

        // Обработчик нажатий
        addPersonButton.setOnClickListener {
            var serviceResponse: QueryResult = QueryResult(false, Data(listOf<News>(), "0", ""))

            // Получение данных
            lifecycleScope.launch(Dispatchers.IO) {
                // Получение ответа от сервиса
                serviceResponse = getQueryResult().getInfo()

                Log.d("!!!!!!!!!!!!!! ", "!!!!!!!!!!!!!!1")

                withContext(Dispatchers.Main) {
                    // Преобразование ответа в список новостей
                    val newsList: List<News> = getNewsList(serviceResponse)
                    // Формирование списка уникальных новостей
                    val uniqueNews: List<News> = getUniqueNews(newsList)

                    // TODO: Разделить отображение и запись в базу данных
                    uniqueNews.forEach {
                        // Передача данныx в адаптер
                        newsAdapter.addNews(it)

                        // Запись в бд
                        insertNews(it)
                    }
                }
            }
        }
        Log.d("!!!!!!!!!!!!!! ", "!!!!!!!!!!!!!!3")

        // Вызов метода для отображения содержимого бд при запуске
        retrieveNews()
    }

    private suspend fun getUniqueNews(newsList: List<News>): List<News> {
        var idNewsList: List<String> = listOf()
        // Извлечение доступных id из бд
        idNewsList = (applicationContext as App).repository.getAllNewsId()

        val result = mutableListOf<News>()
        newsList.forEach {
            if (it.id !in idNewsList)
                result.add(it)
        }
        return result
    }

    private fun insertNews(news: News) {
        // lifecycleScope существует в пределах жизни активити
        lifecycleScope.launch(Dispatchers.IO) {
            (applicationContext as App).repository.insert(news = news)
        }
    }

    private fun initRecyclerView(app: AppCompatActivity) {
        newsAdapter = NewsAdapter(object : MyOnClickListener{
            override fun onClicked(mobile_url: String?) {

                val intent = Intent(app, BrowserActivity::class.java) // контекст и класс
                intent.putExtra("url", mobile_url ?: "")
                startActivity(intent)

                Log.d("Activity", mobile_url ?: "")
            }
        })

        with(newsList) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = newsAdapter
            this.setHasFixedSize(true)
        }
    }

    // Извлекаем и отображаем содержимое бд
    private fun retrieveNews() {
        // Работа в фоновом потоке
        lifecycleScope.launch(Dispatchers.IO) {
            val news = (applicationContext as App).repository.getAllNews()
            // Работа в главном потоке
            withContext(Dispatchers.Main) {
                newsAdapter.setNews(news)
            }
        }
    }
}