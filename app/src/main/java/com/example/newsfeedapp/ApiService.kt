import com.example.testapp.model.QueryResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://ws-tszh-1c-test.vdgb-soft.ru/api/mobile/news/list/"

interface ApiService {
    @GET(BASE_URL)
    suspend fun getInfo(): QueryResult
}

fun getQueryResult() : ApiService{
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Передача имени класса и получение сервиса
    val service: ApiService = retrofit.create(ApiService::class.java)

    return service
}