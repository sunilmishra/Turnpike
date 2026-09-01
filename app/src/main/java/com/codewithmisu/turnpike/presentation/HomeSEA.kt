package com.codewithmisu.turnpike.presentation

import com.codewithmisu.turnpike.domain.Driver
import com.codewithmisu.turnpike.domain.DriverStatus
import com.codewithmisu.turnpike.domain.Ride

data class HomeState(
    val driver: Driver? = null,
    val currentRide: Ride? = null,
    val incomingRideCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface HomeEvent {
    data class LoadDriver(val driverId: String) : HomeEvent
    data class ChangeStatus(val driverId: String, val status: DriverStatus) : HomeEvent
    data class RefreshIncoming(val driverId: String) : HomeEvent
    data class RefreshCurrentRide(val driverId: String) : HomeEvent
}


sealed interface HomeEffect {
    data class ShowMessage(val message: String) : HomeEffect
}