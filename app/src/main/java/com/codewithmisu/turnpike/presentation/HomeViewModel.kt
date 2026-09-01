package com.codewithmisu.turnpike.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithmisu.turnpike.domain.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.LoadDriver -> loadDriver(event.driverId)

            is HomeEvent.ChangeStatus ->
                changeStatus(event.driverId, event.status)

            is HomeEvent.RefreshIncoming ->
                refreshIncoming(event.driverId)

            is HomeEvent.RefreshCurrentRide ->
                refreshCurrentRide(event.driverId)
        }
    }

    private fun loadDriver(driverId: String) = viewModelScope.launch {
        reduce { it.copy(isLoading = true) }

        val driver = repository.getDriver(driverId)
        reduce { it.copy(driver = driver, isLoading = false) }

        refreshIncoming(driverId)
        refreshCurrentRide(driverId)
    }

    private fun changeStatus(driverId: String, status: DriverStatus) =
        viewModelScope.launch {
            repository.updateDriverStatus(driverId, status)
            loadDriver(driverId)
            sendEffect(HomeEffect.ShowMessage("Status updated"))
        }

    private fun refreshIncoming(driverId: String) = viewModelScope.launch {
        val incoming = repository.getIncomingRides(driverId)
        reduce { it.copy(incomingRideCount = incoming.size) }
    }

    private fun refreshCurrentRide(driverId: String) = viewModelScope.launch {
        val ride = repository.getActiveRide(driverId)
        reduce { it.copy(currentRide = ride) }
    }

    private fun reduce(block: (HomeState) -> HomeState) {
        _state.value = block(_state.value)
    }

    private fun sendEffect(effect: HomeEffect) = viewModelScope.launch {
        _effect.send(effect)
    }
}

