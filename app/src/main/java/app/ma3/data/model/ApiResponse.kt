package app.ma3.data.model

/**
 * This is obsolete
 */
data class RouteResponse(
    val id: String?,
    val from: String?,
    val to: String?,
    val steps: List<RouteStepResponse>?,
    val totalFare: Double?,
    val currency: String? = "KSH"
)

data class RouteStepResponse(
    val stepNumber: Int?,
    val instruction: String?,
    val fare: Double?
)