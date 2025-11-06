package app.ma3.data.network

import app.ma3.data.api.MatatuApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Simple network module - easy to extend with auth, interceptors, etc.
 */
object NetworkModule {

    private const val BASE_URL = "http://192.168.1.11:8000/api/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: MatatuApiService by lazy {
        retrofit.create(MatatuApiService::class.java)
    }
}
