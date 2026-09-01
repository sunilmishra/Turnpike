package com.codewithmisu.turnpike.presentation

import com.codewithmisu.turnpike.domain.Ride

data class IncomingRideState(
    val rides: List<Ride> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface IncomingRideEvent {
    data class LoadIncoming(val driverId: String) : IncomingRideEvent
    data class AcceptRide(val rideId: String) : IncomingRideEvent
    data class DeclineRide(val rideId: String) : IncomingRideEvent
}

sealed interface IncomingRideEffect {
    data class NavigateToCurrentRide(val rideId: String) : IncomingRideEffect
    data class ShowMessage(val message: String) : IncomingRideEffect
}
