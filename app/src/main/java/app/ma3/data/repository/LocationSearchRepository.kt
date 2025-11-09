package app.ma3.data.repository

import app.ma3.data.api.LocationSearchService
import app.ma3.data.network.NetworkModule
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class LocationSearchRepository(
    private val searchService: LocationSearchService = NetworkModule.locationSearchService
) {
    suspend fun searchLocation(query: String): Result<List<LocationSearchResult>> {
        if (query.length < 3) {
            return Result.success(emptyList())
        }

        return try {
            val response = searchService.searchLocation(query)

            if (response.isSuccessful && response.body() != null) {
                val results = response.body()!!.map { nominatim ->
                    LocationSearchResult(
                        displayName = nominatim.display_name,
                        latitude = nominatim.lat.toDouble(),
                        longitude = nominatim.lon.toDouble(),
                        type = nominatim.type
                    )
                }
                Result.success(results)
            } else {
                Result.failure(Exception("Search failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

@Parcelize
data class LocationSearchResult(
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
    val type: String
): Parcelable

