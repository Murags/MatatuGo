package app.ma3.ui.components.routeResultsScreen

data class Route(
    val from: String,
    val to: String,
    val cost: Int,
    val duration: String,
    val matatus: Int,
    val isOptimal: Boolean = false
    // TODO: add the logic for when two or more routes share the same optimal cost. Maybe we can look at distance next. Or display both?
)
