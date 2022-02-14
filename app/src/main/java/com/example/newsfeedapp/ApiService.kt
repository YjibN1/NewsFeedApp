
import com.example.testapp.model.QueryResult
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(BASE_URL)
    suspend fun getInfo(): QueryResult
}