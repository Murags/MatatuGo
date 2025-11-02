package app.ma3.signin

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import kotlin.collections.listOf


import app.ma3.ui.theme.MatatuOrange
import app.ma3.ui.theme.MatatuYellow
import app.ma3.ui.theme.WarmWhite
import androidx.compose.ui.text.font.FontWeight


@Composable
fun SignInScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MatatuYellow.copy(alpha = 0.2f),
                        MatatuOrange.copy(alpha = 0.1f)
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 80.dp)
        ) {
            // App title
            Text(
                text = "MatatuGo",
                style = MaterialTheme.typography.headlineLarge,
                color = MatatuOrange,
                fontWeight = FontWeight.Bold
            )

            // Tagline
            Text(
                text = "Navigate Kenya’s routes with ease.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B6B6B),
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
