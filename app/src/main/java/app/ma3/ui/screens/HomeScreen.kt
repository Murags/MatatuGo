package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.ui.components.routeDetailsScreen.RouteMapPlaceholder
import app.ma3.ui.components.publicComponents.RouteHeader
import app.ma3.ui.components.LocationSearchField
import app.ma3.data.repository.LocationSearchResult
import app.ma3.ui.theme.MatatuOrange
import app.ma3.ui.theme.MatatuYellow

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToRouteResults: (Double, Double, Double, Double) -> Unit = { _, _, _, _ -> },
    onNavigateToHelp: () -> Unit = {},
) {
    var originLocation by rememberSaveable { mutableStateOf<LocationSearchResult?>(null) }
    var destinationLocation by rememberSaveable { mutableStateOf<LocationSearchResult?>(null) }
    var selectedTab by remember { mutableStateOf(0) }
    var isLoadingLocation by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission && originLocation == null) {
            isLoadingLocation = true
            getCurrentLocation(
                context = context,
                onLocationReceived = { lat, lon ->
                    originLocation = LocationSearchResult(
                        displayName = "Current Location",
                        latitude = lat,
                        longitude = lon,
                        type = "current_location"
                    )
                    isLoadingLocation = false
                },
                onError = {
                    isLoadingLocation = false
                }
            )
        }
    }

    // Location permission launcher (for when auto-fetch needs permission)
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            // Permission granted, get location
            isLoadingLocation = true
            getCurrentLocation(
                context = context,
                onLocationReceived = { lat, lon ->
                    originLocation = LocationSearchResult(
                        displayName = "Current Location",
                        latitude = lat,
                        longitude = lon,
                        type = "current_location"
                    )
                    isLoadingLocation = false
                },
                onError = {
                    isLoadingLocation = false
                }
            )
        } else {
            isLoadingLocation = false
        }
    }

    // Request permission on first load if not granted
    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Use theme defaults for RouteHeader to match other screens
                RouteHeader(
                    title = "MatatuGo",
                    onBackClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    showBackArrow = false
                )

                IconButton(
                    onClick = onNavigateToProfile,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = Color.Black,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onNavigateToHelp = onNavigateToHelp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                LocationSearchField(
                    label = if (isLoadingLocation) "Getting your location..." else "Your Location",
                    selectedLocation = originLocation,
                    onLocationSelected = { originLocation = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LocationSearchField(
                    label = "Destination",
                    selectedLocation = destinationLocation,
                    onLocationSelected = { destinationLocation = it },
                    leadingIcon = Icons.Default.Search
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val origin = originLocation
                        val destination = destinationLocation
                        if (origin != null && destination != null) {
                            onNavigateToRouteResults(
                                origin.latitude,
                                origin.longitude,
                                destination.latitude,
                                destination.longitude
                            )
                        }
                    },
                    enabled = originLocation != null && destinationLocation != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MatatuYellow,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Find Cheapest Route",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RouteMapPlaceholder(
                fromLocation = originLocation?.displayName ?: "Your Location",
                toLocation = destinationLocation?.displayName ?: "Destination",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Nearby",
                    tint = colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Nearby Stages",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onNavigateToHelp: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    NavigationBar(
        containerColor = Color.White,
        contentColor = colors.onBackground
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        BottomNavItem(
            icon = Icons.AutoMirrored.Filled.Help,
            label = "Help",
            selected = selectedTab == 1,
            onClick = {
                onTabSelected(1)
                onNavigateToHelp()
            }
        )
    }
}

/**
 * Get current device location using Google Play Services
 */
private fun getCurrentLocation(
    context: android.content.Context,
    onLocationReceived: (Double, Double) -> Unit,
    onError: () -> Unit
) {
    try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val cancellationTokenSource = CancellationTokenSource()

        // Check permission again
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    onLocationReceived(location.latitude, location.longitude)
                } else {
                    onError()
                }
            }.addOnFailureListener {
                onError()
            }
        } else {
            onError()
        }
    } catch (e: SecurityException) {
        onError()
    }
}

@Composable
fun RowScope.BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp),
                tint = if (selected) colors.primary else colors.onSurface.copy(alpha = 0.6f)
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 10.sp,
                color = if (selected) colors.primary else colors.onSurface.copy(alpha = 0.7f)
            )
        },
        selected = selected,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = colors.primary,
            selectedTextColor = colors.primary,
            unselectedIconColor = colors.onSurface.copy(alpha = 0.6f),
            unselectedTextColor = colors.onSurface.copy(alpha = 0.6f),
            indicatorColor = colors.primary.copy(alpha = 0.12f)
        )
    )
}
