package app.ma3.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToRouteDetails: () -> Unit = {},
    onNavigateToRouteResults: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToProfile) {
            Text("Go to Profile")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToRouteDetails) {
            Text("View Route Details")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToRouteResults) {
            Text("View Route Results")
        }
    }
}
