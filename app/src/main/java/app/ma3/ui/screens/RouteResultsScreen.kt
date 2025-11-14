package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.ma3.data.repository.RouteData
import app.ma3.ui.components.publicComponents.RouteHeader
import app.ma3.ui.components.routeResultsScreen.OptimalRouteCard
import app.ma3.ui.components.routeResultsScreen.AlternativeRouteCard
import app.ma3.ui.components.routeResultsScreen.RouteTipCard
import app.ma3.ui.theme.*
import app.ma3.ui.viewmodel.RouteResultsViewModel

@Composable
fun RouteResultsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToRouteDetails: (RouteData) -> Unit = {},
    originLat: Double? = null,
    originLon: Double? = null,
    destLat: Double? = null,
    destLon: Double? = null,
    viewModel: RouteResultsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(originLat, originLon, destLat, destLon) {
        if (originLat != null && originLon != null && destLat != null && destLon != null) {
            viewModel.fetchRoutesByCoordinates(
                originLat = originLat,
                originLon = originLon,
                destLat = destLat,
                destLon = destLon
            )
        }
    }

    // Helper to parse integer cost from formatted totalFare like "KSH 120"
    fun RouteData.costInt(): Int = totalFare.filter { it.isDigit() }.toIntOrNull() ?: Int.MAX_VALUE
    val routes = uiState.routes.sortedBy { it.costInt() }

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
    ) {
        RouteHeader(
            title = "Your Routes",
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                uiState.error != null -> {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Error: ${uiState.error}",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            if (originLat != null && originLon != null && destLat != null && destLon != null) {
                                Button(onClick = {
                                    viewModel.fetchRoutesByCoordinates(
                                        originLat = originLat,
                                        originLon = originLon,
                                        destLat = destLat,
                                        destLon = destLon
                                    )
                                }) {
                                    Text("Retry Search")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Button(
                                onClick = { onNavigateBack() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text("Try Different Locations")
                            }
                        }
                    }
                }

                routes.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No routes found for the given criteria.",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }

                else -> {
                    // Best Route Section Header
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Best Route",
                                tint = GreenPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Best Route",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = GraySectionTitle
                            )
                        }
                    }

                    // Optimal Route Card
                    item {
                        OptimalRouteCard(route = routes.first(), onNavigateToRouteDetails)
                    }

                    // Alternative Routes Section Header
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = "Alternative Routes",
                                tint = GraySectionIcon,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Alternative Routes",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = GraySectionTitle
                            )
                        }
                    }

                    // Alternative Route Cards
                    if (routes.size > 1) {
                        items(routes.drop(1)) { route ->
                            AlternativeRouteCard(
                                route = route,
                                cheapestCost = routes.first().costInt(),
                                onNavigateToRouteDetails = onNavigateToRouteDetails
                            )
                        }
                    }

                    // Route Tip Card
                    item {
                        RouteTipCard()
                    }
                }
            }
        }
    }
}
