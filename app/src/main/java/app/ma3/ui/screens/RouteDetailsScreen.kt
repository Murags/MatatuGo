package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.ma3.ui.components.*

@Composable
fun RouteDetailsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Sample data - in a real app, this would come from parameters or state
    val sampleSteps = listOf(
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

        // Map section
        RouteMapPlaceholder(
            fromLocation = "Kencom",
            toLocation = "Buru Buru",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Route steps
        RouteStepsList(
            steps = sampleSteps,
            totalFare = "Ksh 120",
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
