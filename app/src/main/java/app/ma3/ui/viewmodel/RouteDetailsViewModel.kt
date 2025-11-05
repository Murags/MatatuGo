package app.ma3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ma3.data.repository.RouteRepository
import app.ma3.ui.components.routeDetailsScreen.RouteStep
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Simple ViewModel for Route Details Screen
 */
class RouteDetailsViewModel(
    private val repository: RouteRepository = RouteRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RouteDetailsUiState())
    val uiState: StateFlow<RouteDetailsUiState> = _uiState.asStateFlow()

    fun loadRouteDetails(routeId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getRouteDetails(routeId).fold(
                onSuccess = { routeData ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        steps = routeData.steps,
                        totalFare = routeData.totalFare,
                        fromLocation = routeData.fromLocation,
                        toLocation = routeData.toLocation,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Unknown error"
                    )
                }
            )
        }
    }

    fun searchRoutes(from: String, to: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.searchRoutes(from, to).fold(
                onSuccess = { routes ->
                    if (routes.isNotEmpty()) {
                        val firstRoute = routes.first()
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            steps = firstRoute.steps,
                            totalFare = firstRoute.totalFare,
                            fromLocation = firstRoute.fromLocation,
                            toLocation = firstRoute.toLocation,
                            error = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = "No routes found"
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Search failed"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun retry() {
        val currentState = _uiState.value
        // Simple retry logic - you can enhance this later
        clearError()
    }
}

data class RouteDetailsUiState(
    val isLoading: Boolean = false,
    val steps: List<RouteStep> = emptyList(),
    val totalFare: String = "",
    val fromLocation: String = "",
    val toLocation: String = "",
    val estimatedTime: String = "",
    val error: String? = null
) {
    val hasData: Boolean get() = steps.isNotEmpty()
}
