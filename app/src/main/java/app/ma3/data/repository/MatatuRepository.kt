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

    suspend fun getRouteDetails(routeId: String): Result<RouteData> {
        return try {
            val response = apiService.getRouteDetails(routeId)

            if (response.isSuccessful && response.body() != null) {
                val apiResponse = response.body()!!

                Result.success(RouteData(
                    id = apiResponse.id ?: routeId,
                    fromLocation = apiResponse.from ?: "Unknown",
                    toLocation = apiResponse.to ?: "Unknown",
                    steps = RouteMapper.toRouteSteps(apiResponse),
                    totalFare = RouteMapper.formatTotalFare(apiResponse)
                ))
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchRoutes(from: String, to: String): Result<List<RouteData>> {
        return try {
            val response = apiService.searchRoutes(from, to)

            if (response.isSuccessful && response.body() != null) {
                val routes = response.body()!!.map { apiResponse ->
                    RouteData(
                        id = apiResponse.id ?: "unknown",
                        fromLocation = apiResponse.from ?: from,
                        toLocation = apiResponse.to ?: to,
                        steps = RouteMapper.toRouteSteps(apiResponse),
                        totalFare = RouteMapper.formatTotalFare(apiResponse)
                    )
                }
                Result.success(routes)
            } else {
                Result.failure(Exception("Search failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

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
                Result.failure(Exception("Coordinate search failed: ${response.code()}"))
            }
        } catch (e: Exception) {
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
