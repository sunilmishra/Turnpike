package com.codewithmisu.turnpike.presentation.history

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
class RideHistoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(RideHistoryState())
    val state: StateFlow<RideHistoryState> = _state.asStateFlow()

    private val _effect = Channel<RideHistoryEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: RideHistoryEvent) {
        when (event) {
            is RideHistoryEvent.LoadHistory ->
                loadHistory(event.driverId)
        }
    }

    private fun loadHistory(driverId: String) = viewModelScope.launch {
        reduce { it.copy(isLoading = true) }

        val rides = repository.getRideHistory(driverId)
        reduce { it.copy(rides = rides, isLoading = false) }
    }

    private fun reduce(block: (RideHistoryState) -> RideHistoryState) {
        _state.value = block(_state.value)
    }

    private fun sendEffect(effect: RideHistoryEffect) = viewModelScope.launch {
        _effect.send(effect)
    }
}
