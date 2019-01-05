package com.olatunji.repository

import com.olatunji.enumeration.Vehicle
import com.olatunji.dto.Courier
import com.olatunji.dto.Location
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CourierRepositoryTest {

    @Test
    fun `find one existing`() {
        val courier = CourierRepository().findById("courier-1")
        val expectedCourier = Courier().withId("courier-1")
            .withBox(true)
            .withName("Manolo Escobar")
            .withVehicle(Vehicle.MOTORCYCLE)
            .withLocation(Location(41.3965463, 2.1963997))

        assertEquals(expectedCourier, courier)
    }

    @Test
    fun findAll() {
        val allCouriers = CourierRepository().findAll()
        assertFalse(allCouriers.isEmpty())
    }
}