package app.ma3.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.R
import app.ma3.ui.theme.MatatuOrange
import app.ma3.ui.theme.MatatuYellow
import kotlinx.coroutines.delay

/**
 * MatatuGo Splash Screen
 *
 * Displays:
 * - Gradient background (orange → yellow)
 * - White Matatu icon
 * - App title and tagline
 * - Animated fade-in
 * - Circular progress indicator
 * - Automatically navigates to Sign-In after a short delay
 */
@Composable
fun SplashScreen(
    onTimeout: () -> Unit = {}
) {
    // Create an alpha animation for fade-in
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Fade in over 1.2s
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200)
        )
        // Stay visible for 2.5s total
        delay(2500)
        onTimeout()
    }

    // Background gradient and content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = alpha.value)
            .background(
                brush = Brush.linearGradient(
                    listOf(MatatuOrange, MatatuYellow)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // White bus icon (ensure ic_bus.xml is white in res/drawable)
            Image(
                painter = painterResource(id = R.drawable.ic_bus),
                contentDescription = "MatatuGo Logo",
                modifier = Modifier
                    .size(96.dp)
                    .padding(bottom = 16.dp)
            )

            // App title
            Text(
                text = "MatatuGo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Tagline
            Text(
                text = "Find your cheapest routes across Nairobi",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 64.dp)
            )

            // Loading indicator
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Loading text
            Text(
                text = "Loading routes ...",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * Preview for Android Studio
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}

