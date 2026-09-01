package com.codewithmisu.turnpike.presentation

import androidx.compose.runtime.Composable
import com.codewithmisu.turnpike.domain.Ride
import com.codewithmisu.turnpike.domain.DriverStatus
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    driverId: String,
    viewModel: HomeViewModel,
    onIncomingClick: (String) -> Unit,
    onOpenMap: (Ride) -> Unit,
    onHistoryClick: (String) -> Unit,
    onShowDrivers: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()
    var showStatusSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.LoadDriver(driverId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Welcome, ${state.driver?.name ?: ""}")
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Driver Status: ")
                Spacer(Modifier.width(24.dp))
                Button(onClick = { showStatusSheet = true }) {
                    Text(text = state.driver?.status?.name ?: "")
                }
            }

            if (showStatusSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showStatusSheet = false },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Change Driver Status",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(12.dp))
                        DriverStatus.entries.forEach { status ->
                            ListItem(
                                headlineContent = { Text(status.name) },
                                modifier = Modifier.clickable {
                                    state.driver?.let {
                                        viewModel.onEvent(HomeEvent.ChangeStatus(it.id, status))
                                    }
                                    scope.launch {
                                        sheetState.hide()
                                        showStatusSheet = false
                                    }
                                }
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Current Ride Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            snackbarHostState
                                .showSnackbar(
                                    message = "Open Google map",
                                    duration = SnackbarDuration.Short
                                )
                        }
                        state.currentRide?.let { onOpenMap(it) }
                    }
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Current Ride")
                        if (state.currentRide != null) {
                            Text("Rider: ${state.currentRide!!.riderName}")
                            Text("ETA: ${state.currentRide!!.etaSeconds ?: "--"} sec")
                        } else {
                            Text("No active ride")
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Right Icon"
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Incoming Ride Request Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onIncomingClick(driverId)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Incoming Requests")
                    Row {
                        Text(
                            "${state.incomingRideCount}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Right Icon"
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Ride History
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onHistoryClick(driverId)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Ride History")
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Right Icon"
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
            // List of Drivers on the Map
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onShowDrivers(driverId)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Drivers on Map")
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Right Icon"
                    )
                }
            }
        }
    }
}
