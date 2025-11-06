package app.ma3.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationSearchService {
    @GET("search")
    suspend fun searchLocation(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("countrycodes") countryCode: String = "ke",
        @Query("limit") limit: Int = 5,
        @Query("addressdetails") addressDetails: Int = 1
    ): Response<List<NominatimResult>>
}

data class NominatimResult(
    val place_id: Long,
    val display_name: String,
    val lat: String,
    val lon: String,
    val type: String,
    val importance: Double
)

