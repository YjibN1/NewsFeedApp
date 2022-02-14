import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.testapp.model.QueryResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://ws-tszh-1c-test.vdgb-soft.ru/api/mobile/news/list/"

// TODO: переименовать
fun getQueryResult() : ApiService{
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Передача ссылки и получение сервиса
    val service: ApiService = retrofit.create(ApiService::class.java)

    return service
}