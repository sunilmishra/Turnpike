package com.codewithmisu.turnpike.presentation.drivers

import com.codewithmisu.turnpike.domain.Driver

data class DriversMapState(
    val drivers: List<Driver> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface DriversMapEvent {
    object LoadDrivers : DriversMapEvent
    object RefreshDrivers : DriversMapEvent
    data class CenterOnMe(val driverId: String) : DriversMapEvent
}
sealed interface DriversMapEffect {
    data class ShowMessage(val message: String) : DriversMapEffect
}
