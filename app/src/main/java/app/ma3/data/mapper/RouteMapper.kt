package app.ma3.data.mapper

import app.ma3.data.model.RouteResponse
import app.ma3.data.model.CoordinateRoutesResponse
import app.ma3.data.model.CoordinateRoute
import app.ma3.data.model.RouteSegment
import app.ma3.data.repository.RouteData
import app.ma3.ui.components.RouteStep

/**
 * Simple mapper to convert API responses to UI models
 */
object RouteMapper {

    fun toRouteSteps(response: RouteResponse): List<RouteStep> {
        return response.steps?.mapIndexed { index, step ->
            RouteStep(
                stepNumber = step.stepNumber ?: (index + 1),
                instruction = step.instruction ?: "Unknown instruction",
                fare = "Ksh ${step.fare?.toInt() ?: 0}"
            )
        } ?: emptyList()
    }

    fun formatTotalFare(response: RouteResponse): String {
        val fare = response.totalFare ?: 0.0
        val currency = response.currency ?: "KSH"
        return "$currency ${fare.toInt()}"
    }

    fun fromCoordinateRoutes(resp: CoordinateRoutesResponse): List<RouteData> {
        val routes = resp.routes ?: emptyList()
        return routes.mapIndexed { index, r ->
            val steps = toStepsFromSegments(r.segments)
            RouteData(
                id = "coord_${r.route_rank ?: (index + 1)}",
                fromLocation = r.origin?.stop_name ?: "Unknown",
                toLocation = r.destination?.stop_name ?: "Unknown",
                steps = steps,
                totalFare = "KSH --" // placeholder
            )
        }
    }

    private fun toStepsFromSegments(segments: List<RouteSegment>?): List<RouteStep> {
        return segments?.mapIndexed { i, s ->
            val label = s.route_label ?: "?"
            val board = s.board ?: "Unknown"
            val alight = s.alight ?: "Unknown"
            RouteStep(
                stepNumber = i + 1,
                instruction = "Board $label at $board → alight at $alight",
                fare = "Ksh --"
            )
        } ?: emptyList()
    }
}
