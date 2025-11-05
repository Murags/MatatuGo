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

    init {
        // placeholder before search is implemented
        fetchRoutesByCoordinates(
            originLat = -1.308665872,
            originLon = 36.81243896,
            destLat = -1.264457703,
            destLon = 36.74703598,
            searchRadius = 100,
            alternatives = 2
        )
    }

    fun fetchRoutes(from: String, to: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.searchRoutes(from, to).fold(
                onSuccess = { routes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        routes = routes,
                        error = null
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load routes"
                    )
                }
            )
        }
    }

    fun fetchRoutesByCoordinates(
        originLat: Double,
        originLon: Double,
        destLat: Double,
        destLon: Double,
        searchRadius: Int = 100,
        alternatives: Int = 2
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.searchRoutesByCoordinates(
                originLat = originLat,
                originLon = originLon,
                destLat = destLat,
                destLon = destLon,
                searchRadius = searchRadius,
                alternatives = alternatives
            ).fold(
                onSuccess = { routes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        routes = routes,
                        error = null
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load routes"
                    )
                }
            )
        }
    }
}

data class RouteResultsUiState(
    val isLoading: Boolean = false,
    val routes: List<RouteData> = emptyList(),
    val error: String? = null
)


