package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.ma3.data.repository.RouteData
import app.ma3.ui.components.publicComponents.RouteHeader
import app.ma3.ui.components.routeResultsScreen.OptimalRouteCard
import app.ma3.ui.components.routeResultsScreen.AlternativeRouteCard
import app.ma3.ui.components.routeResultsScreen.RouteTipCard
import app.ma3.ui.theme.*
import app.ma3.ui.viewmodel.RouteResultsViewModel
import app.ma3.ui.components.icons.NoRouteIcon

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

                uiState.error != null || routes.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(32.dp)
                            ) {
                                Icon(
                                    imageVector = NoRouteIcon,
                                    contentDescription = "No routes found",
                                    modifier = Modifier.size(120.dp),
                                    tint = Color(0xFFE74C3C)
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "No route found for that destination",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2C3E50),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Please check your destination\nor try again later",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(32.dp))

                                Button(
                                    onClick = { onNavigateBack() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MatatuYellow,
                                        contentColor = Color.Black
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Text(
                                        text = "Try Again",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
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
