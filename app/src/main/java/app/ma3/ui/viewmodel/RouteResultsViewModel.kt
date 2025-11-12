package app.ma3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ma3.data.repository.RouteData
import app.ma3.data.repository.RouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RouteResultsViewModel(
    private val repository: RouteRepository = RouteRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RouteResultsUiState())
    val uiState: StateFlow<RouteResultsUiState> = _uiState.asStateFlow()

    fun fetchRoutesByCoordinates(
        originLat: Double,
        originLon: Double,
        destLat: Double,
        destLon: Double,
        searchRadius: Int = 500,
        alternatives: Int = 3
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val radiiToTry = listOf(searchRadius, 1000, 1500)
            var lastError: String? = null

            for ((index, radius) in radiiToTry.withIndex()) {
                val result = repository.searchRoutesByCoordinates(
                    originLat = originLat,
                    originLon = originLon,
                    destLat = destLat,
                    destLon = destLon,
                    searchRadius = radius,
                    alternatives = alternatives
                )

                result.fold(
                    onSuccess = { routes ->
                        if (routes.isNotEmpty()) {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                routes = routes,
                                error = null
                            )
                            return@launch
                        } else {
                            lastError = "No routes found. Trying larger search area..."
                        }
                    },
                    onFailure = { e ->
                        lastError = e.message
                    }
                )
                if (index < radiiToTry.size - 1) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = "No routes found with ${radius}m radius. Expanding search to ${radiiToTry[index + 1]}m..."
                    )
                    kotlinx.coroutines.delay(500)
                }
            }

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = lastError ?: "No routes found after trying search radii up to 1500m. Try different locations or check if your backend has data for this area."
            )
        }
    }
}

data class RouteResultsUiState(
    val isLoading: Boolean = false,
    val routes: List<RouteData> = emptyList(),
    val error: String? = null
)


