package app.ma3.data.mapper

import app.ma3.data.model.CoordinateRoutesResponse
import app.ma3.data.model.RouteSegment
import app.ma3.data.repository.RouteData
import app.ma3.ui.components.routeDetailsScreen.RouteStep

/**
 * Simple mapper to convert API responses to UI models
 */
object RouteMapper {

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
