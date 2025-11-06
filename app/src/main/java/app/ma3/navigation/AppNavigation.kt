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

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteResults = { navController.navigate(Routes.ROUTE_RESULTS) },
                onNavigateToHelp = { navController.navigate(Routes.HELP) }
            )
        }

        composable(Routes.SIGNIN) {
            SignInScreen(
                onNavigateToHomeScreen = { navController.navigate(Routes.HOME) },
                onNavigateToRouteResults = { navController.navigate(Routes.ROUTE_RESULTS) }
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
            RouteResultsScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToRouteDetails = { route ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedRoute", route)
                    navController.navigate(Routes.ROUTE_DETAILS)
                }
            )
        }

        composable(Routes.HELP) {
            HelpScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
