package app.ma3.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.ui.components.RouteHeader
import androidx.compose.material3.Icon

data class Route(
    val from: String,
    val to: String,
    val cost: Int,
    val duration: String,
    val matatus: Int,
    val isOptimal: Boolean = false
    // TODO: add the logic for when two or more routes share the same optimal cost. Maybe we can look at distance next. Or display both?
)

@Composable
fun RouteResultsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val routes = listOf(
        Route(
            from = "Kencom",
            to = "Buruburu",
            cost = 50,
            duration = "25 min",
            matatus = 1,
            isOptimal = true
        ),
        Route(
            from = "Kencom → City Stadium",
            to = "Buruburu",
            cost = 70,
            duration = "30 min",
            matatus = 2
        ),
        Route(
            from = "Kencom → Muthurwa",
            to = "Buruburu",
            cost = 80,
            duration = "35 min",
            matatus = 2
        ),
        Route(
            from = "Kencom → Jogoo Road → Jericho",
            to = "Buruburu",
            cost = 90,
            duration = "40 min",
            matatus = 3
        )
    ).sortedBy { it.cost }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFDF7),
                        Color(0xFFF1F1F1)
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
                        tint = Color(0xFF059669),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Best Route",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
                    )
                }
            }

            // Optimal Route Card
            item {
                OptimalRouteCard(route = routes.first())
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
                        tint = Color(0xFF6B7280),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Alternative Routes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937)
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

            // Info Tip
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFDEEBFF)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "💡",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column {
                            Text(
                                text = "Route Tip",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E3A8A)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Routes are sorted by total cost. The cheapest option considers fare and then distance (if fares are the same).",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF1E40AF)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OptimalRouteCard(route: Route) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0xFF059669).copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF0FDF4)
        ),
        border = BorderStroke(2.dp, Color(0xFF10B981))
    ) {
        Box {
            // "CHEAPEST" Badge
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                color = Color(0xFF059669),
                shape = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "CHEAPEST",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Route Path
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color(0xFF059669), shape = RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = route.from,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "→",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF059669)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = route.to,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Color(0xFFF97316), shape = RoundedCornerShape(6.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.7f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.AccessTime,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xFF6B7280)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Duration",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF6B7280)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = route.duration,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.7f)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.DirectionsBus,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = Color(0xFF6B7280)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Matatus",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF6B7280)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = route.matatus.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Price and Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFF059669),
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "Total Cost",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(
                                text = "Ksh ${route.cost}",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF97316)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = "View Details",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlternativeRouteCard(route: Route, cheapestCost: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, Color(0xFFE5E7EB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Route Path
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color(0xFF3B82F6), shape = RoundedCornerShape(4.dp))
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                val routePath = run {
                    val fromTrim = route.from.trim()
                    val toTrim = route.to.trim()
                    // if 'from' already contains the destination at the end, don't append it again
                    if (fromTrim.endsWith(toTrim, ignoreCase = true)) {
                        fromTrim
                    } else {
                        "$fromTrim → $toTrim"
                    }
                }

                Text(
                    text = routePath,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth(),
                    softWrap = true,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Stats
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = route.duration,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B7280)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.DirectionsBus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${route.matatus} matatu${if (route.matatus > 1) "s" else ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6B7280)
                        )
                    }
                }

                // Price and Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Cost",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "Ksh ${route.cost}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "+Ksh ${route.cost - cheapestCost} more",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFF97316)
                        )
                    }

                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF3F4F6)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "View",
                            color = Color(0xFF374151),
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
