package app.ma3.data.model

data class CoordinateRoutesResponse(
    val routing_strategy: String?,
    val alternatives_count: Int?,
    val routes: List<CoordinateRoute>?
)

data class CoordinateRoute(
    val rank: Int?,
    val transfers: Int?,
    val algorithm_cost: Double?,
    val estimated_total_fare_ksh: Int?,
    val steps: List<RouteStep>?
)

data class RouteStep(
    val board: String?,
    val alight: String?,
    val board_coordinates: LatLon?,
    val alight_coordinates: LatLon?,
    val route_id: String?,
    val matatu_number: String?,
    val stops: List<String>?,
    val stop_count: Int?,
    val estimated_fare_ksh: Int?,
    val raw_routing_cost: Int?
)

data class LatLon(
    val lat: Double?,
    val lon: Double?
)


