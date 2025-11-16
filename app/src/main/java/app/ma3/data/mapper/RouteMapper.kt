package app.ma3.data.mapper

import app.ma3.data.model.CoordinateRoutesResponse
import app.ma3.data.model.RouteStep
import app.ma3.data.repository.RouteData
import app.ma3.ui.components.routeDetailsScreen.RouteStep as UIRouteStep

/**
 * Simple mapper to convert API responses to UI models
 */
object RouteMapper {

    fun fromCoordinateRoutes(resp: CoordinateRoutesResponse): List<RouteData> {
        val routes = resp.routes ?: emptyList()
        return routes.mapIndexed { index, r ->
            val steps = toStepsFromSteps(r.steps)
            RouteData(
                id = "coord_${r.rank ?: (index + 1)}",
                fromLocation = r.steps?.firstOrNull()?.board ?: "Unknown",
                toLocation = r.steps?.lastOrNull()?.alight ?: "Unknown",
                steps = steps,
                totalFare = "KSH ${r.estimated_total_fare_ksh ?: 0}"
            )
        }
    }

    private fun toStepsFromSteps(steps: List<RouteStep>?): List<UIRouteStep> {
        return steps?.mapIndexed { i, s ->
            val matatuNumber = s.matatu_number ?: "?"
            val board = s.board ?: "Unknown"
            val alight = s.alight ?: "Unknown"
            UIRouteStep(
                stepNumber = i + 1,
                instruction = "Board $matatuNumber at $board → alight at $alight",
                fare = "Ksh ${s.estimated_fare_ksh ?: 0}"
            )
        } ?: emptyList()
    }
}
