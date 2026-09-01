package com.codewithmisu.turnpike.presentation.history

import com.codewithmisu.turnpike.domain.Ride

data class RideHistoryState(
    val rides: List<Ride> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface RideHistoryEvent {
    data class LoadHistory(val driverId: String) : RideHistoryEvent
}

sealed interface RideHistoryEffect {
    data class NavigateToRideDetails(val rideId: String) : RideHistoryEffect
}

