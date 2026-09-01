package com.codewithmisu.turnpike.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomingRideScreen(
    driverId: String,
    viewModel: IncomingRideViewModel,
    onNavigateToCurrentRide: (String) -> Unit,
    onBackPressed: (() -> Unit)
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(IncomingRideEvent.LoadIncoming(driverId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is IncomingRideEffect.NavigateToCurrentRide ->
                    onNavigateToCurrentRide(effect.rideId)

                is IncomingRideEffect.ShowMessage ->
                    println(effect.message) // Replace with snack bar
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Incoming Rides") },
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
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            state.rides.forEach { ride ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text("Rider: ${ride.riderName}")
                        Text("Pickup: ${ride.pickup.latitude}, ${ride.pickup.longitude}")
                        Text("ETA: ${ride.etaSeconds ?: "--"} sec")
                        Text("Fare: $${ride.fareEstimate ?: "--"}")

                        Spacer(Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                viewModel.onEvent(IncomingRideEvent.DeclineRide(ride.id))
                            }) {
                                Text("Decline")
                            }

                            Button(onClick = {
                                viewModel.onEvent(IncomingRideEvent.AcceptRide(ride.id))
                            }) {
                                Text("Accept")
                            }
                        }
                    }
                }
            }
        }
    }
}
