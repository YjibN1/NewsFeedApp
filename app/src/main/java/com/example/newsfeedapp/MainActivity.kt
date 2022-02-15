package com.example.newsfeedapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeedapp.model.News
import com.example.newsfeedapp.model.QueryResult
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
        // Обработчик нажатия на FloatingActionButton
        refreshNewsButton.setOnClickListener {
            var serviceResponse: QueryResult
            lifecycleScope.launch(Dispatchers.IO) {
                // Получение ответа от сервиса
                serviceResponse = getQueryResult().getInfo()

                withContext(Dispatchers.Main) {
                    val newsList: List<News> = getNewsList(serviceResponse)
                    val uniqueNews: List<News> = getUniqueNews(newsList)
                    uniqueNews.forEach {
                        newsAdapter.addNews(it)
                    }
                    // Запись новостей в бд
                    uniqueNews.forEach {
                        insertNews(it)
                    }
                }
            }
        }
        // Отображение содержимого бд при создании активити
        retrieveNews()
    }

    /**
     * Иницилазация RecyclerView
     */
    private fun initRecyclerView(app: AppCompatActivity) {
        newsAdapter = NewsAdapter(object : OnClickListener {
            // Обработчик нажатия на ячейку новости
            override fun onClicked(mobile_url: String?) {
                val intent = Intent(app, BrowserActivity::class.java)
                intent.putExtra("url", mobile_url ?: "")
                startActivity(intent)
            }
        })

        with(newsList) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = newsAdapter
            this.setHasFixedSize(true)
        }
    }

    /**
     * Извлечение списка новостей из [qRes].
     * @param [qRes] Ответ от сервиса.
     * @return Пустой список, если ответ пустой.
     */
    private fun getNewsList(qRes: QueryResult) = qRes.data?.news ?: listOf()

    /**
     * Отбирает из [newsList] уникальные новости (которых нет в бд).
     * @param[newsList] Исходный список новостей.
     */
    private suspend fun getUniqueNews(newsList: List<News>): List<News> {
        val idNewsList = (applicationContext as App).repository.getAllNewsId()
        val result = mutableListOf<News>()
        newsList.forEach {
            if (it.id !in idNewsList)
                result.add(it)
        }
        return result.toList()
    }

    /**
     * Запись [news] в бд.
     * @param [news] Новость.
     */
    private fun insertNews(news: News) =
        lifecycleScope.launch(Dispatchers.IO) {
            (applicationContext as App).repository.insert(news = news)
        }

    /**
     * Извлечение и отображение новостей на RecyclerView
     */
    private fun retrieveNews() =
        lifecycleScope.launch(Dispatchers.IO) {
            val newsList = (applicationContext as App).repository.getAllNews()
            withContext(Dispatchers.Main) {
                newsAdapter.setNews(newsList)
            }
        }
}