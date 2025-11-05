package app.ma3.data.api

import app.ma3.data.model.RouteResponse
import app.ma3.data.model.CoordinateRoutesResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Simple API service for matatu routes
 */
interface MatatuApiService {
    @GET("routes/by-coordinates")
    suspend fun getRoutesByCoordinates(
        @Query("origin_lat") originLat: Double,
        @Query("origin_lon") originLon: Double,
        @Query("dest_lat") destLat: Double,
        @Query("dest_lon") destLon: Double,
        @Query("search_radius") searchRadius: Int = 100,
        @Query("alternatives") alternatives: Int = 2
    ): Response<CoordinateRoutesResponse>
}
