package app.ma3.data.model

data class CoordinateRoutesResponse(
    val request: CoordinateRequestPayload?,
    val alternatives_count: Int?,
    val routes: List<CoordinateRoute>?,
    val optimization: OptimizationInfo? = null
)

data class CoordinateRequestPayload(
    val origin_coordinates: LatLon?,
    val destination_coordinates: LatLon?,
    val search_radius_m: Int?
)

data class LatLon(
    val lat: Double?,
    val lon: Double?
)

data class CoordinateRoute(
    val route_rank: Int?,
    val origin: StopEndpoint?,
    val destination: StopEndpoint?,
    val transfers: Int?,
    val segments: List<RouteSegment>?,
    val total_walking_distance_m: Int?,
    val estimated_total_time_min: Int?,
    val optimization_score: Double?
)

data class StopEndpoint(
    val stop_id: String?,
    val stop_name: String?,
    val coordinates: LatLon?,
    val walking_distance_m: Int?,
    val walking_time_min: Int?
)

data class RouteSegment(
    val route_id: String?,
    val board: String?,
    val stops: List<String>?,
    val alight: String?,
    val route_label: String?
)

data class OptimizationInfo(
    val candidates_tested: Int?,
    val total_combinations_evaluated: Int?
)


