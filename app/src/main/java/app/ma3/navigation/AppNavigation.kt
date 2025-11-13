package app.ma3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.ma3.ui.screens.HomeScreen
import app.ma3.ui.screens.ProfileScreen
import app.ma3.ui.screens.RouteDetailsScreen
import app.ma3.ui.screens.RouteResultsScreen
import app.ma3.ui.screens.SignInScreen
import app.ma3.ui.screens.SignUpScreen
import app.ma3.ui.screens.HelpScreen
import app.ma3.data.repository.RouteData

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SIGNIN,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteResults = { originLat, originLon, destLat, destLon ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.apply {
                            set("originLat", originLat)
                            set("originLon", originLon)
                            set("destLat", destLat)
                            set("destLon", destLon)
                        }
                    navController.navigate(Routes.ROUTE_RESULTS)
                },
                onNavigateToHelp = { navController.navigate(Routes.HELP) }
            )
        }

        composable(Routes.SIGNIN) {
            SignInScreen(
                onNavigateToHomeScreen = { navController.navigate(Routes.HOME) },
                onNavigateToRouteResults = { navController.navigate(Routes.ROUTE_RESULTS) },
                onNavigateToSignUp = { navController.navigate(Routes.SIGN_UP) }
            )
        }

        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onNavigateToSignIn = { navController.navigate(Routes.SIGNIN) },
                onSignUpSuccess = { navController.navigate(Routes.HOME) }
            )
        }

        // Profile Screen
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // Route Details Screen
        composable(Routes.ROUTE_DETAILS) {
            val selectedRoute = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RouteData>("selectedRoute")

            RouteDetailsScreen(
                onNavigateBack = { navController.navigateUp() },
                routeData = selectedRoute
            )
        }

        composable(Routes.ROUTE_RESULTS) {
            val originLat = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("originLat")
            val originLon = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("originLon")
            val destLat = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("destLat")
            val destLon = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("destLon")

            RouteResultsScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToRouteDetails = { route ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedRoute", route)
                    navController.navigate(Routes.ROUTE_DETAILS)
                },
                originLat = originLat,
                originLon = originLon,
                destLat = destLat,
                destLon = destLon
            )
        }

        composable(Routes.HELP) {
            HelpScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
