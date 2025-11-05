package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import app.ma3.ui.theme.*
import app.ma3.ui.components.publicComponents.RouteHeader
import app.ma3.ui.components.routeResultsScreen.Route
import app.ma3.ui.components.routeResultsScreen.OptimalRouteCard
import app.ma3.ui.components.routeResultsScreen.AlternativeRouteCard
import app.ma3.ui.components.routeResultsScreen.RouteTipCard

@Composable
fun RouteResultsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToRouteDetails: () -> Unit = {}
) {
    val routes = listOf(
        Route("Kencom", "Buruburu", 50, "25 min", 1, true),
        Route("Kencom → City Stadium", "Buruburu", 70, "30 min", 2),
        Route("Kencom → Muthurwa", "Buruburu", 80, "35 min", 2),
        Route("Kencom → Jogoo Road → Jericho", "Buruburu", 90, "40 min", 3)
    ).sortedBy { it.cost }

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

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
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
                        imageVector = Icons.Filled.TrendingUp,
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
            items(routes.drop(1)) { route ->
                AlternativeRouteCard(
                    route = route,
                    cheapestCost = routes.first().cost
                )
            }

            // Route Tip Card
            item {
                RouteTipCard()
            }
        }
    }
}
