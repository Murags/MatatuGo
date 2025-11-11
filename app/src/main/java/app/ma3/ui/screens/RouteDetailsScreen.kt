package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import app.ma3.data.repository.RouteData
import app.ma3.ui.components.publicComponents.RouteHeader
import app.ma3.ui.components.routeDetailsScreen.MapboxMapComponent
import app.ma3.ui.components.routeDetailsScreen.RouteStep
import app.ma3.ui.components.routeDetailsScreen.RouteStepsList
import app.ma3.ui.theme.LightGrayBg
import app.ma3.ui.theme.WarmWhite

@Composable
fun RouteDetailsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    routeData: RouteData? = null
) {
    // Fallback sample data for offline/development mode
    val fallbackSteps = listOf(
        RouteStep(
            stepNumber = 1,
            instruction = "Board Matatu 32 at Kencom",
            fare = "Ksh 50"
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
        val from = routeData?.fromLocation ?: "Kencom"
        val to = routeData?.toLocation ?: "Buru Buru"
        val steps = routeData?.steps ?: fallbackSteps
        val totalFare = routeData?.totalFare ?: "Ksh 120"

        MapboxMapComponent(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(32.dp))

        RouteStepsList(
            steps = steps,
            totalFare = totalFare,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
