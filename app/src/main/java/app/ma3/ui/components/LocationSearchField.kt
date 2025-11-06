package app.ma3.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import app.ma3.data.repository.LocationSearchResult
import app.ma3.ui.viewmodel.LocationSearchViewModel
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun LocationSearchField(
    label: String,
    selectedLocation: LocationSearchResult?,
    onLocationSelected: (LocationSearchResult?) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector = Icons.Default.LocationOn,
    viewModel: LocationSearchViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val colors = MaterialTheme.colorScheme

    // Update searchQuery when selectedLocation changes
    LaunchedEffect(selectedLocation) {
        if (selectedLocation != null && selectedLocation.displayName.isNotEmpty()) {
            searchQuery = selectedLocation.displayName
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
                isExpanded = true
                viewModel.searchLocation(newValue)
                if (newValue.isEmpty()) {
                    onLocationSelected(null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = colors.onSurface
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        isExpanded = false
                        onLocationSelected(null)
                        viewModel.clearResults()
                    }) {
                        Icon(Icons.Default.Close, "Clear")
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface,
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.onSurface.copy(alpha = 0.12f),
                cursorColor = colors.primary
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )

        if (isExpanded && searchQuery.length >= 3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .offset(y = (-4).dp),
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                )
            ) {
                if (isSearching) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                } else if (searchResults.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            "No results found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.onSurface.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 300.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(searchResults) { result ->
                            ListItem(
                                headlineContent = {
                                    Text(
                                        result.displayName,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colors.onSurface,
                                        maxLines = 2
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        result.type.replaceFirstChar { it.uppercase() },
                                        fontSize = 13.sp,
                                        color = colors.primary.copy(alpha = 0.7f)
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        leadingIcon,
                                        null,
                                        tint = colors.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                modifier = Modifier
                                    .clickable {
                                        onLocationSelected(result)
                                        searchQuery = result.displayName
                                        isExpanded = false
                                        viewModel.clearResults()
                                    }
                                    .padding(horizontal = 8.dp, vertical = 2.dp),
                                colors = ListItemDefaults.colors(
                                    containerColor = colors.surface
                                )
                            )
                            if (result != searchResults.last()) {
                                HorizontalDivider(
                                    color = colors.onSurface.copy(alpha = 0.06f),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


