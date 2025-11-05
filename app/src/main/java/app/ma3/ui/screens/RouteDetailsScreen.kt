package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.ma3.ui.components.publicComponents.RouteHeader
import app.ma3.ui.components.routeDetailsScreen.RouteMapPlaceholder
import app.ma3.ui.components.routeDetailsScreen.RouteStep
import app.ma3.ui.components.routeDetailsScreen.RouteStepsList
import app.ma3.ui.theme.LightGrayBg
import app.ma3.ui.theme.WarmWhite

@Composable
fun RouteDetailsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Sample data - in a real app, this would come from parameters or state or props
    // TODO: intergrate with retrofit for fetch these
    val sampleSteps = listOf(
        RouteStep(
            stepNumber = 1,
            instruction = "Board Matatu 32 at Kencom",
            fare = "Ksh 50" // TODO CHANGE THIS TO NUMBER
        ),
        RouteStep(
            stepNumber = 2,
            instruction = "Alight at Buru Buru Stage",
            fare = "Ksh 70"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        WarmWhite,
                        LightGrayBg
                    )
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        RouteHeader(
            title = "Route Details",
            onBackClick = onNavigateBack
        )
        Spacer(modifier = Modifier.height(24.dp))

        RouteMapPlaceholder(
            fromLocation = "Kencom",
            toLocation = "Buru Buru",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        RouteStepsList(
            steps = sampleSteps,
            totalFare = "Ksh 120", // TODO CHANGE THIS TO NUMBER WHICH IS CALCULATED
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
