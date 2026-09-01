package com.codewithmisu.turnpike.data

import com.codewithmisu.turnpike.domain.*
import kotlin.time.Clock

val mockDrivers = mutableListOf(
    Driver(
        id = "A101",
        name = "John Doe",
        vehicle = Vehicle(
            plateNumber = "ABC123",
            model = "Toyota Prius"
        ),
        status = DriverStatus.AVAILABLE,
        geoPoint = GeoPoint(
            latitude = 37.7749,
            longitude = -122.4194
        )
    ),
    Driver(
        id = "B201",
        name = "Tom Hank",
        vehicle = Vehicle(
            plateNumber = "XYZ789",
            model = "Honda Civic"
        ),
        status = DriverStatus.BUSY,
        geoPoint = GeoPoint(
            latitude = 34.0522,
            longitude = -118.2437
        )
    ),
    Driver(
        id = "C301",
        name = "Mike Johnson",
        vehicle = Vehicle(
            plateNumber = "DEF456",
            model = "Tesla Model S"
        ),
        status = DriverStatus.OFFLINE,
        geoPoint = GeoPoint(
            latitude = 40.7128,
            longitude = -74.0060
        )
    ),
    Driver(
        id = "D401",
        name = "Sarah Lee",
        vehicle = Vehicle(
            plateNumber = "GHI789",
            model = "BMW X5"
        ),      status = DriverStatus.AVAILABLE,
        geoPoint = GeoPoint(
            latitude = 34.0522,
            longitude = -118.2437
        )
    ),
    Driver(
        id = "E501",
        name = "David Kim",
        vehicle = Vehicle(
            plateNumber = "JKL012",
            model = "Ford Explorer"
        ),
        status = DriverStatus.BUSY,
        geoPoint = GeoPoint(
            latitude = 40.7128,
            longitude = -74.0060
        )
    )
)


val mockRides = mutableListOf(
    Ride(
        id = "R101",
        driver = mockDrivers[0],
        riderName = "Shane Watson",
        status = RideStatus.Accepted,
        pickup = GeoPoint(
            latitude = 37.7749,
            longitude = -122.4194
        ),
        dropOff = GeoPoint(
            latitude = 34.00522,
            longitude = -118.2437
        ),
        etaSeconds = 1200,
        requestedAt = Clock.System.now().toEpochMilliseconds(),
        fareEstimate = 10.50
    ),
    Ride(
        id = "R201",
        driver = mockDrivers[0],
        riderName = "David Warner",
        status = RideStatus.Requested,
        pickup = GeoPoint(
            latitude = 34.0522,
            longitude = -118.2437
        ),
        dropOff = GeoPoint(
            latitude = 40.7128,
            longitude = -74.0060,
        ),
        requestedAt = Clock.System.now().toEpochMilliseconds(),
        fareEstimate = 12.75
    ),
    Ride(
        id = "R301",
        driver = mockDrivers[0],
        riderName = "Ricky Ponting",
        status = RideStatus.Completed,
        pickup = GeoPoint(
            latitude = 34.0522,
            longitude = -118.2437
        ),
        dropOff = GeoPoint(
            latitude = 40.7128,
            longitude = -74.0060,
        ),
        requestedAt = Clock.System.now().toEpochMilliseconds(),
        completedAt = Clock.System.now().toEpochMilliseconds() + 3600000,
        fareEstimate = 12.75
    )
)


