package com.example.newsfeedapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.lifecycleScope
import com.example.testapp.model.Data
import com.example.newsfeedapp.model.News
import com.example.testapp.model.QueryResult
import getQueryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        // Обработчик нажатий
        addPersonButton.setOnClickListener {
            var serviceResponse: QueryResult = QueryResult(false, Data(listOf<News>(), "0", ""))

            // Получение данных
            lifecycleScope.launch(Dispatchers.IO) {
                // Получаем ответ
                serviceResponse = getQueryResult().getInfo()
                Log.d("!!!!!!!!!!!!!! ", "!!!!!!!!!!!!!!1")

                withContext(Dispatchers.Main) {
                    // Получаем список данных
                    val newsList: List<News> = getNewsList(serviceResponse)
                    Log.d("!!!!!!!!!!!!!! ", "!!!!!!!!!!!!!!2")


                    newsList.forEach {
                        // Передача данныч в адаптер
                        newsAdapter.addNews(it)

                        // Запись в бд
                        insertNews(it)
                    }
                }
            }
        }
        Log.d("!!!!!!!!!!!!!! ", "!!!!!!!!!!!!!!3")


        // Вызываем метод отображения бд при запуске
        retrieveNews()
    }

    private fun insertNews(news: News) {
        // lifecycleScope существует в пределах жизни активити
        lifecycleScope.launch(Dispatchers.IO) {
            (applicationContext as App).repository.insert(news = news)
        }
    }
    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(this)

        with(newsList) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = newsAdapter
            this.setHasFixedSize(true)
        }
    }

    private fun retrieveNews() {
        // Work on background thread
        lifecycleScope.launch(Dispatchers.IO) {
            val news = (applicationContext as App).repository.getAllNews()
            // Work on main thread

            withContext(Dispatchers.Main) {
                newsAdapter.setNews(news)
            }
        }
    }

    override fun onItemClicked(id: String) {
        TODO("Заглушка для нажатия")
    }
}



