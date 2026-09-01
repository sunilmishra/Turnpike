package com.codewithmisu.turnpike.presentation.drivers

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithmisu.turnpike.domain.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DriversMapViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(DriversMapState())
    val state: StateFlow<DriversMapState> = _state.asStateFlow()

    private val _effect = Channel<DriversMapEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: DriversMapEvent) {
        when (event) {
            DriversMapEvent.LoadDrivers -> loadDrivers()
            DriversMapEvent.RefreshDrivers -> loadDrivers()
            is DriversMapEvent.CenterOnMe -> centerOnMe(event.driverId)
        }
    }

    private fun loadDrivers() = viewModelScope.launch {
        reduce { it.copy(isLoading = true) }

        val drivers = repository.getDrivers()
        reduce { it.copy(drivers = drivers, isLoading = false) }
    }

    private fun centerOnMe(driverId: String) = viewModelScope.launch {
        sendEffect(DriversMapEffect.ShowMessage("Centering on driver $driverId"))
    }

    private fun reduce(block: (DriversMapState) -> DriversMapState) {
        _state.value = block(_state.value)
    }

    private fun sendEffect(effect: DriversMapEffect) = viewModelScope.launch {
        _effect.send(effect)
    }
}
