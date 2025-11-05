package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.ma3.ui.components.*
import app.ma3.data.repository.RouteData

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
                        Color(0xFFFFFDF7), // #FFFDF7
                        Color(0xFFF1F1F1)  // #F1F1F1
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

        RouteMapPlaceholder(
            fromLocation = from,
            toLocation = to,
            modifier = Modifier.padding(horizontal = 16.dp)
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
