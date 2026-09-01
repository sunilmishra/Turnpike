package com.codewithmisu.turnpike.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.codewithmisu.turnpike.domain.Repository
import com.codewithmisu.turnpike.domain.RideStatus

@HiltViewModel
class IncomingRideViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(IncomingRideState())
    val state: StateFlow<IncomingRideState> = _state.asStateFlow()

    private val _effect = Channel<IncomingRideEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: IncomingRideEvent) {
        when (event) {
            is IncomingRideEvent.LoadIncoming ->
                loadIncoming(event.driverId)

            is IncomingRideEvent.AcceptRide ->
                acceptRide(event.rideId)

            is IncomingRideEvent.DeclineRide ->
                declineRide(event.rideId)
        }
    }

    private fun loadIncoming(driverId: String) = viewModelScope.launch {
        reduce { it.copy(isLoading = true) }

        val rides = repository.getIncomingRides(driverId)
        reduce { it.copy(rides = rides, isLoading = false) }
    }

    private fun acceptRide(rideId: String) = viewModelScope.launch {
        repository.updateRideStatus(rideId, RideStatus.Accepted)
        sendEffect(IncomingRideEffect.NavigateToCurrentRide(rideId))
    }

    private fun declineRide(rideId: String) = viewModelScope.launch {
        repository.updateRideStatus(rideId, RideStatus.Declined)
        reduce { it.copy(rides = it.rides.filterNot { r -> r.id == rideId }) }
        sendEffect(IncomingRideEffect.ShowMessage("Ride declined"))
    }

    private fun reduce(block: (IncomingRideState) -> IncomingRideState) {
        _state.value = block(_state.value)
    }

    private fun sendEffect(effect: IncomingRideEffect) = viewModelScope.launch {
        _effect.send(effect)
    }
}
