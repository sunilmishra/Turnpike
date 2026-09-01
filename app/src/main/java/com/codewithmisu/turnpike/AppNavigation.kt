package com.codewithmisu.turnpike

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.codewithmisu.turnpike.domain.Ride
import com.codewithmisu.turnpike.presentation.HomeScreen
import com.codewithmisu.turnpike.presentation.HomeViewModel
import com.codewithmisu.turnpike.presentation.IncomingRideScreen
import com.codewithmisu.turnpike.presentation.IncomingRideViewModel
import com.codewithmisu.turnpike.presentation.drivers.DriversMapScreen
import com.codewithmisu.turnpike.presentation.drivers.DriversMapViewModel
import com.codewithmisu.turnpike.presentation.history.RideHistoryScreen
import com.codewithmisu.turnpike.presentation.history.RideHistoryViewModel
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val driverId = "A101"
    val backStack = rememberNavBackStack(HomeRoute(driverID = driverId))

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            homeNavigator(
                driverID = driverId,
                onIncomingClick = {
                    backStack.add(IncomingRideRoute(driverID = it))
                },
                onOpenMap = {
                    // Handle open map action
                },
                onHistoryClick = {
                    backStack.add(RideHistoryRoute(driverID = it))
                },
                onShowDrivers = {
                    backStack.add(DriversListRoute(driverID = it))
                }
            )
            incomingRideNavigator(
                driverID = driverId,
                onNavigateToCurrentRide = {
                    backStack.removeLastOrNull()
                },
                onNavigateToHome = {
                    backStack.removeLastOrNull()
                }
            )
            rideHistoryNavigator(
                driverID = driverId,
                onNavigateToCurrentRide = {
                    backStack.removeLastOrNull()
                },
                onNavigateToHome = {
                    backStack.removeLastOrNull()
                }
            )
            driversListNavigator(
                driverID = driverId,
                onNavigateToHome = {
                    backStack.removeLastOrNull()
                }
            )
        }
    )
}

@Serializable
data class HomeRoute(val driverID: String) : NavKey

fun EntryProviderScope<NavKey>.homeNavigator(
    driverID: String,
    onIncomingClick: (driverID: String) -> Unit,
    onOpenMap: (ride: Ride) -> Unit,
    onHistoryClick: (driverID: String) -> Unit,
    onShowDrivers: (driverID: String) -> Unit
) {
    entry<HomeRoute> {
        val homeViewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
            driverId = driverID,
            viewModel = homeViewModel,
            onIncomingClick = onIncomingClick,
            onOpenMap = onOpenMap,
            onHistoryClick = onHistoryClick,
            onShowDrivers = onShowDrivers
        )
    }
}

// Incoming Ride Navigator
@Serializable
data class IncomingRideRoute(val driverID: String) : NavKey

fun EntryProviderScope<NavKey>.incomingRideNavigator(
    driverID: String,
    onNavigateToCurrentRide: (rideID: String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    entry<IncomingRideRoute> {
        val incomingRideViewModel: IncomingRideViewModel = hiltViewModel()
        IncomingRideScreen(
            driverId = driverID,
            viewModel = incomingRideViewModel,
            onNavigateToCurrentRide = onNavigateToCurrentRide,
            onBackPressed = onNavigateToHome
        )
    }
}


// Ride History
@Serializable
data class RideHistoryRoute(val driverID: String) : NavKey

fun EntryProviderScope<NavKey>.rideHistoryNavigator(
    driverID: String,
    onNavigateToCurrentRide: (rideID: String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    entry<RideHistoryRoute> {
        val rideHistoryViewModel: RideHistoryViewModel = hiltViewModel()
        RideHistoryScreen(
            driverId = driverID,
            viewModel = rideHistoryViewModel,
            onNavigateToRideDetails = onNavigateToCurrentRide,
            onBackPressed = onNavigateToHome
        )
    }
}

// Drivers List
@Serializable
data class DriversListRoute(val driverID: String) : NavKey

fun EntryProviderScope<NavKey>.driversListNavigator(
    driverID: String,
    onNavigateToHome: () -> Unit
) {
    entry<DriversListRoute> {
        val driversListViewModel: DriversMapViewModel = hiltViewModel()
        DriversMapScreen(
            driverId = driverID,
            viewModel = driversListViewModel,
            onBackPressed = onNavigateToHome
        )
    }
}