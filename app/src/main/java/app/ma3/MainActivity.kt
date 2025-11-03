package app.ma3

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.ma3.ui.theme.Ma3Theme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Handle the real splash screen
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        super.onCreate(savedInstanceState)
        // 2. Set the main content of the app
        setContent {
            Ma3Theme {
                MatatuGoApp()
            }
        }
    }
}

@Composable
fun MatatuGoApp() {
    val navController: NavHostController = rememberNavController()

    // Start navigation at the first real screen
    NavHost(
        navController = navController,
        startDestination = "signup"
    ) {
//        composable("splash") {
//            SplashScreen(navController)
//        }

        composable("signup") {
            SignUpScreen()
        }

        composable("home") {
            HomeScreen()
        }
    }
}
@Composable
fun SignUpScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text("Sign Up Screen", color = Color.Black, textAlign = TextAlign.Center)
    }
//    androidx.compose.material3.Text(
//        text = "Sign Up Screen",
//        color = androidx.compose.ui.graphics.Color.Black,
//        modifier = androidx.compose.ui.Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.Center)
//    )
}

@Composable
fun HomeScreen() {
//    androidx.compose.material3.Text(
//        text = "Home Screen",
//        color = androidx.compose.ui.graphics.Color.Black,
//        modifier = androidx.compose.ui.Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.Center)
//    )
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text("Sign Up Screen", color = Color.Black, textAlign = TextAlign.Center)
    }
}
