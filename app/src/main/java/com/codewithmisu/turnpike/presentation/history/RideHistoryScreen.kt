package com.codewithmisu.turnpike.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideHistoryScreen(
    driverId: String,
    viewModel: RideHistoryViewModel,
    onNavigateToRideDetails: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(RideHistoryEvent.LoadHistory(driverId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RideHistoryEffect.NavigateToRideDetails ->
                    onNavigateToRideDetails(effect.rideId)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ride History") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPressed()
                        }
                    ) {
                        Icon(
                            Icons.Default.ChevronLeft,
                            contentDescription = "Back",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)) {
            state.rides.forEach { ride ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onNavigateToRideDetails(ride.id)
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("Ride ID: ${ride.id}")
                        Text("Rider: ${ride.riderName}")
                        Text("Fare: $${ride.fareEstimate ?: "--"}")
                        Text("Completed: ${ride.completedAt ?: "--"}")

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "View Details →",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
