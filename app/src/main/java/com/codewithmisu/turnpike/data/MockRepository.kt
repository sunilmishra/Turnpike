package com.codewithmisu.turnpike.data

import com.codewithmisu.turnpike.domain.*
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class MockRepository : Repository {

    // Simulate network delay for realism
    private suspend fun simulateDelay() = delay(300.milliseconds)

    // ---------------------------------------------------------
    // Driver
    // ---------------------------------------------------------
    override suspend fun getDriver(driverId: String): Driver {
        simulateDelay()
        return mockDrivers.first { it.id == driverId }
    }

    override suspend fun updateDriverStatus(driverId: String, status: DriverStatus) {
        simulateDelay()
        val index = mockDrivers.indexOfFirst { it.id == driverId }
        if (index != -1) {
            val driver = mockDrivers[index]
            mockDrivers[index] = driver.copy(status = status)
        }
    }

    override suspend fun updateDriverLocation(driverId: String, geoPoint: GeoPoint) {
        simulateDelay()
        val index = mockDrivers.indexOfFirst { it.id == driverId }
        if (index != -1) {
            val driver = mockDrivers[index]
            mockDrivers[index] = driver.copy(geoPoint = geoPoint)
        }
    }

    override suspend fun getDrivers(): List<Driver> {
        simulateDelay()
        return mockDrivers.toList()
    }

    // ---------------------------------------------------------
    // Incoming Ride Requests
    // ---------------------------------------------------------
    override suspend fun getIncomingRides(driverId: String): List<Ride> {
        simulateDelay()
        return mockRides.filter {
            it.driver.id == driverId && it.status == RideStatus.Requested
        }
    }

    override suspend fun assignRideToDriver(driverId: String, ride: Ride) {
        simulateDelay()
        val driver = mockDrivers.first { it.id == driverId }
        mockRides.add(ride.copy(driver = driver, status = RideStatus.Requested))
    }

    // ---------------------------------------------------------
    // Active Ride
    // ---------------------------------------------------------
    override suspend fun getActiveRide(driverId: String): Ride? {
        simulateDelay()
        return mockRides.firstOrNull {
            it.driver.id == driverId &&
                    (it.status == RideStatus.Accepted || it.status == RideStatus.InProgress)
        }
    }

    // ---------------------------------------------------------
    // Ride Lifecycle
    // ---------------------------------------------------------
    override suspend fun updateRideStatus(rideId: String, status: RideStatus) {
        simulateDelay()
        val index = mockRides.indexOfFirst { it.id == rideId }
        if (index != -1) {
            val ride = mockRides[index]
            mockRides[index] = ride.copy(status = status)
        }
    }

    override suspend fun startRide(rideId: String) {
        updateRideStatus(rideId, RideStatus.InProgress)
    }

    override suspend fun completeRide(rideId: String) {
        val index = mockRides.indexOfFirst { it.id == rideId }
        if (index != -1) {
            val ride = mockRides[index]
            mockRides[index] = ride.copy(
                status = RideStatus.Completed,
                completedAt = System.currentTimeMillis()
            )
        }
    }

    // ---------------------------------------------------------
    // Ride History
    // ---------------------------------------------------------
    override suspend fun getRideHistory(driverId: String): List<Ride> {
        simulateDelay()
        return mockRides.filter {
            it.driver.id == driverId && it.status == RideStatus.Completed
        }
    }
}
