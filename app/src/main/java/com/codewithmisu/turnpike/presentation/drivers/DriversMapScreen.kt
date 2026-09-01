package com.codewithmisu.turnpike.presentation.drivers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversMapScreen(
    driverId: String,
    viewModel: DriversMapViewModel,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(DriversMapEvent.LoadDrivers)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DriversMapEffect.ShowMessage ->
                    println(effect.message) // Replace with Snack bar
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drivers List") },
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
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Map goes here\n\n")
                Text("\n\nAPI Key is Required to render map.")

            }
            Text(state.drivers.toString())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    viewModel.onEvent(DriversMapEvent.CenterOnMe(driverId))
                }) {
                    Text("Center on Me")
                }

                Button(onClick = {
                    viewModel.onEvent(DriversMapEvent.RefreshDrivers)
                }) {
                    Text("Refresh")
                }
            }
        }
    }
}
