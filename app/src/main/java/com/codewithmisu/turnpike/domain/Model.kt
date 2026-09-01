package com.codewithmisu.turnpike.domain

data class Vehicle(
    val plateNumber: String,
    val model: String,
)

enum class DriverStatus {
    AVAILABLE,
    BUSY,
    OFFLINE,
}

data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
)

data class Driver(
    val id: String,
    val name: String,
    val vehicle: Vehicle,
    val status: DriverStatus,
    val geoPoint: GeoPoint
)

enum class RideStatus {
    // Requested by a rider
    Requested,
    // Driver Accepted
    Accepted,
    // Driver Decline
    Declined,
    // Ride In Progress
    InProgress,
    // Ride Completed
    Completed,
}

data class Ride(
    val id: String,
    val driver: Driver,
    val riderName: String,
    val status: RideStatus,
    val pickup: GeoPoint,
    val dropOff: GeoPoint,
    val etaSeconds: Int? = null,
    val requestedAt: Long,
    val completedAt: Long? = null,
    val fareEstimate: Double? = null,
)
