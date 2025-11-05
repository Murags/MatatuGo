package app.ma3.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.R
import app.ma3.ui.theme.MatatuOrange
import app.ma3.ui.theme.MatatuYellow
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToSignIn: () -> Unit
) {
    // Infinite rotation animation for the loading icon
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // 🔹 Automatically navigate to SignIn after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigateToSignIn()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MatatuOrange,
                        MatatuYellow
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // App Icon
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bus),
                    contentDescription = "Bus Icon",
                    tint = MatatuOrange,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Title
            Text(
                text = "MatatuGo",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Find your cheapest routes across Nairobi",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Rotating loading icon
            Icon(
                painter = painterResource(id = R.drawable.ic_loading),
                contentDescription = "Loading Spinner",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(rotation)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Loading text
            Text(
                text = "Loading routes ...",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
