package app.ma3.data.repository

import app.ma3.data.api.MatatuApiService
import app.ma3.data.mapper.RouteMapper
import app.ma3.data.network.NetworkModule
import app.ma3.ui.components.routeDetailsScreen.RouteStep
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Simple repository for route data
 */
class RouteRepository(
    private val apiService: MatatuApiService = NetworkModule.apiService
) {
    suspend fun searchRoutesByCoordinates(
        originLat: Double,
        originLon: Double,
        destLat: Double,
        destLon: Double,
        searchRadius: Int = 100,
        alternatives: Int = 2
    ): Result<List<RouteData>> {
        return try {
            val response = apiService.getRoutesByCoordinates(
                originLat = originLat,
                originLon = originLon,
                destLat = destLat,
                destLon = destLon,
                searchRadius = searchRadius,
                alternatives = alternatives
            )

            if (response.isSuccessful && response.body() != null) {
                val mapped = RouteMapper.fromCoordinateRoutes(response.body()!!)
                Result.success(mapped)
            } else {
                val errorMsg = when (response.code()) {
                    404 -> "Backend API not found."
                    500 -> "Server error: ${response.message()}"
                    else -> "API error (${response.code()}): ${response.message()}"
                }
                android.util.Log.e("MatatuRepository", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: java.net.ConnectException) {
            val errorMsg = "Cannot connect to server"
            android.util.Log.e("MatatuRepository", errorMsg, e)
            Result.failure(Exception(errorMsg))
        } catch (e: Exception) {
            android.util.Log.e("MatatuRepository", "Unexpected error: ${e.message}", e)
            Result.failure(e)
        }
    }
}

@Parcelize
data class RouteData(
    val id: String,
    val fromLocation: String,
    val toLocation: String,
    val steps: List<RouteStep>,
    val totalFare: String
): Parcelable
