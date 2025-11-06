package app.ma3.ui.components.routeResultsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.ui.theme.BlueInfoBg
import app.ma3.ui.theme.BlueInfoText
import app.ma3.ui.theme.BlueInfoTitle

@Composable
fun RouteTipCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueInfoBg
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
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = BlueInfoTitle
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Routes are sorted by total cost. The cheapest option considers fare and then distance (if fares are the same).",
                    style = MaterialTheme.typography.bodySmall,
                    color = BlueInfoText
                )
            }
        }
    }
}
