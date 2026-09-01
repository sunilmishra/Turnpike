package com.codewithmisu.turnpike.domain

interface Repository {

    suspend fun getDriver(driverId: String): Driver

    suspend fun updateDriverStatus(
        driverId: String,
        status: DriverStatus
    )

    suspend fun updateDriverLocation(
        driverId: String,
        geoPoint: GeoPoint
    )

    suspend fun getDrivers(): List<Driver>

    suspend fun getIncomingRides(driverId: String): List<Ride>

    /**
     * Simulates assigning a new ride to a driver.
     */
    suspend fun assignRideToDriver(
        driverId: String,
        ride: Ride
    )

    suspend fun getActiveRide(driverId: String): Ride?


    suspend fun updateRideStatus(
        rideId: String,
        status: RideStatus
    )

    suspend fun startRide(rideId: String)

    suspend fun completeRide(rideId: String)


    suspend fun getRideHistory(driverId: String): List<Ride>
}
