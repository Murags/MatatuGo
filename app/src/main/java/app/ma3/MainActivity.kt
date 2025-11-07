package app.ma3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.ma3.navigation.AppNavigation
import app.ma3.ui.theme.Ma3Theme
// import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // Uncomment if using Android 12+ system splash screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Optional: If you are using the modern Android 12+ system splash screen API:
        // val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- NEW STATE: Tracks when the Compose splash screen is visually complete ---
        var isSplashFinished by mutableStateOf(false)

        // Optional: If using the system splash screen API, hold it visible until 'isSplashFinished' is true
        // splashScreen.setKeepOnScreenCondition { !isSplashFinished }

        setContent {
            Ma3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        // --- NEW CALLBACK: This is triggered by SplashScreen when navigation is executed ---
                        onSplashFinished = {
                            isSplashFinished = true
                        }
                    )
                }
            }
        }
    }
}