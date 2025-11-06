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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.ui.components.routeDetailsScreen.RouteMapPlaceholder
import app.ma3.ui.components.publicComponents.RouteHeader

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToRouteResults: () -> Unit = {},
    onNavigateToHelp: () -> Unit = {},
) {

    var yourLocation by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Use theme defaults for RouteHeader to match other screens
                RouteHeader(
                    title = "MatatuGo",
                    onBackClick = {},
                    modifier = Modifier.fillMaxWidth()
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
                        tint = colors.onPrimary,
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
                // Match RouteResults / RouteDetails gradient background
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFDF7), // WarmWhite (same as Theme)
                            Color(0xFFF1F1F1)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = yourLocation,
                    onValueChange = { yourLocation = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    placeholder = {
                        Text(
                            "Your Location",
                            color = colors.onSurface.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = colors.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.onSurface,
                        unfocusedTextColor = colors.onSurface.copy(alpha = 0.9f),
                        disabledTextColor = colors.onSurface.copy(alpha = 0.6f),
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface,
                        focusedBorderColor = colors.primary,
                        unfocusedBorderColor = colors.onSurface.copy(alpha = 0.12f),
                        cursorColor = colors.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    placeholder = {
                        Text(
                            "Destination",
                            color = colors.onSurface.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = colors.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = colors.onSurface,
                        unfocusedTextColor = colors.onSurface.copy(alpha = 0.9f),
                        disabledTextColor = colors.onSurface.copy(alpha = 0.6f),
                        focusedContainerColor = colors.surface,
                        unfocusedContainerColor = colors.surface,
                        focusedBorderColor = colors.primary,
                        unfocusedBorderColor = colors.onSurface.copy(alpha = 0.12f),
                        cursorColor = colors.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Use theme primary so button color matches RouteHeader (MatatuYellow)
                Button(
                    onClick = onNavigateToRouteResults,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Find Cheapest Route",
                        color = colors.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RouteMapPlaceholder(
                fromLocation = if (yourLocation.isBlank()) "Your Location" else yourLocation,
                toLocation = if (destination.isBlank()) "Destination" else destination,
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
        containerColor = colors.background,
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
                modifier = Modifier.size(24.dp),
                tint = if (selected) colors.primary else colors.onSurface.copy(alpha = 0.6f)
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 12.sp,
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
