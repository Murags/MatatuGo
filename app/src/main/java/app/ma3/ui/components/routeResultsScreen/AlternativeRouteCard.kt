package app.ma3.ui.components.routeResultsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.ma3.data.repository.RouteData
import app.ma3.ui.theme.AltCardBg
import app.ma3.ui.theme.AltCardBorder
import app.ma3.ui.theme.BlueRouteDot
import app.ma3.ui.theme.GrayButtonBg
import app.ma3.ui.theme.GrayButtonText
import app.ma3.ui.theme.GraySectionIcon
import app.ma3.ui.theme.OrangePrimary
import kotlin.text.trim

@Composable
fun AlternativeRouteCard(route: RouteData, cheapestCost: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = AltCardBg
        ),
        border = BorderStroke(2.dp, AltCardBorder),
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
                        .background(BlueRouteDot, shape = RoundedCornerShape(4.dp))
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                val routePath = run {
                    val fromTrim = route.fromLocation.trim()
                    val toTrim = route.toLocation.trim()
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
                            tint = GraySectionIcon
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${route.steps.size} steps",
                            style = MaterialTheme.typography.bodySmall,
                            color = GraySectionIcon
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.DirectionsBus,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GraySectionIcon
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Stops ${route.steps.size}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GraySectionIcon
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
                            color = GraySectionIcon
                        )
                        Text(
                            text = route.totalFare,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "+Ksh ${(route.totalFare.filter { it.isDigit() }.toIntOrNull() ?: 0) - cheapestCost} more",
                            style = MaterialTheme.typography.labelSmall,
                            color = OrangePrimary
                        )
                    }

                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GrayButtonBg
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "View",
                            color = GrayButtonText,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
