package app.ma3.data.network

import android.content.Context
import app.ma3.data.api.AuthApiService
import app.ma3.data.api.MatatuApiService
import app.ma3.data.api.LocationSearchService
import app.ma3.data.preferences.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Simple network module - easy to extend with auth, interceptors, etc.
 */
object NetworkModule {

    private const val BASE_URL = "http://192.168.1.11:8000/api/"
    private const val NOMINATIM_URL = "https://nominatim.openstreetmap.org/"

    private lateinit var tokenManager: TokenManager

    fun init(context: Context) {
        tokenManager = TokenManager(context)
    }

    private val authInterceptor = Interceptor { chain ->
        val token = tokenManager.getAccessTokenSync()
        val request = chain.request().newBuilder()
            .addHeader("User-Agent", "MatatuGo-Android/1.0")

        if (!token.isNullOrEmpty()) {
            request.addHeader("Authorization", "Bearer $token")
        }

        chain.proceed(request.build())
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val authRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val nominatimRetrofit = Retrofit.Builder()
        .baseUrl(NOMINATIM_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApiService: AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }

    val apiService: MatatuApiService by lazy {
        retrofit.create(MatatuApiService::class.java)
    }

    val locationSearchService: LocationSearchService by lazy {
        nominatimRetrofit.create(LocationSearchService::class.java)
    }
}
