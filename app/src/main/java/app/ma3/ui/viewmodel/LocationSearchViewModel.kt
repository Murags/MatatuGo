package app.ma3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ma3.data.repository.LocationSearchRepository
import app.ma3.data.repository.LocationSearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class LocationSearchViewModel(
    private val repository: LocationSearchRepository = LocationSearchRepository()
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<LocationSearchResult>>(emptyList())
    val searchResults: StateFlow<List<LocationSearchResult>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private var searchJob: Job? = null

    fun searchLocation(query: String) {
        searchJob?.cancel()

        if (query.length < 3) {
            _searchResults.value = emptyList()
            return
        }

        searchJob = viewModelScope.launch {
            delay(500)
            _isSearching.value = true

            repository.searchLocation(query).fold(
                onSuccess = { results ->
                    _searchResults.value = results
                    _isSearching.value = false
                },
                onFailure = {
                    _searchResults.value = emptyList()
                    _isSearching.value = false
                }
            )
        }
    }

    fun clearResults() {
        _searchResults.value = emptyList()
    }
}

