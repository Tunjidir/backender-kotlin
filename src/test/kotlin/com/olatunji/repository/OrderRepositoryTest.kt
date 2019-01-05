package com.olatunji.repository

import com.olatunji.dto.Location
import com.olatunji.dto.Order
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderRepositoryTest {
    private lateinit var orderRepository: OrderRepository

    @BeforeAll
    fun setup() {
        orderRepository = OrderRepository(
            5.0,
            0.5,
            1.0,
            "pizza,cake,flamingo",
            0,
            1,
            2,
            3,
            4,
            CourierRepository()
        )
    }

    @Test
    fun findAll() {
        val orders = orderRepository.findAll()
        assertFalse(orders.isEmpty())

        val firstOrder = orders[0]

        val expected = Order().withId("order-1")
            .withDescription("I want a pizza cut into very small slices")
            .withFood(true)
            .withVip(false)
            .withPickup(Location(41.3965463, 2.1963997))
            .withDelivery(Location(41.407834, 2.1675979))

        assertEquals(firstOrder, expected)
    }

    @Test
    fun `find by courier id with glovo box and motorcyle`() {
        val orders = orderRepository.findByCourierId("courier-1")
        assertFalse(orders.isEmpty())

        assertEquals(5, orders.size)
        val firstOrder = orders[0]

        val expected = Order().withId("order-1")
            .withDescription("I want a pizza cut into very small slices")
            .withFood(true)
            .withVip(false)
            .withPickup(Location(41.3965463, 2.1963997))
            .withDelivery(Location(41.407834, 2.1675979))

        assertEquals(firstOrder, expected)
    }

    @Test
    fun `find by courierId without glovoBox and motorcycle`() {
        val orders = orderRepository.findByCourierId("courier-3")
        assertFalse(orders.isEmpty())
        assertEquals(2, orders.size)

        val firstOrder = orders[0]
        val secondOrder = orders[1]

        val firstExpected = Order().withId("order-5")
            .withDescription("Hot dog")
            .withFood(true)
            .withVip(false)
            .withPickup(Location(41.40638606893252,2.166255699226159))
            .withDelivery(Location(41.40052857611856,2.17474693857396))

        val secondExpected = Order().withId("order-3")
            .withDescription("Envelope")
            .withFood(false)
            .withVip(true)
            .withPickup(Location(41.37790607439139,2.1801331715968426))
            .withDelivery(Location(41.480661712089115,2.1760928408928155))

        assertEquals(firstExpected, firstOrder)
        assertEquals(secondExpected, secondOrder)
    }

    @Test
    fun `find by courierId with glovoBox and bicycle`() {
        val orders = orderRepository.findByCourierId("courier-6")
        assertFalse(orders.isEmpty())
        assertEquals(4, orders.size)

        val firstOrder = orders[0]

        val expectedOrder = Order().withId("order-2")
            .withDescription("1x Hot dog with Fries\n2x Kebab with Fries\nChocolate cake")
            .withFood(true)
            .withVip(false)
            .withPickup(Location(41.38412925150105, 2.1870953755511464))
            .withDelivery(Location(41.39265932307547, 2.1743998837459806))

        assertEquals(firstOrder, expectedOrder)
    }

    @Test
    fun `find by courierId without glovoBox and bicycle`() {
        val orders = orderRepository.findByCourierId("courier-2")
        assertFalse(orders.isEmpty())
        assertEquals(1, orders.size)

        val firstOrder = orders[0]

        val expectedOrder = Order().withId("order-5")
            .withDescription("Hot dog")
            .withFood(true)
            .withVip(false)
            .withPickup(Location(41.40638606893252,2.166255699226159))
            .withDelivery(Location(41.40052857611856,2.17474693857396))

        assertEquals(firstOrder, expectedOrder)
    }

    @Test
    fun `find by courierId updated glovoBoxWords`() {
        val orderRepository = OrderRepository(
            5.0,
            0.5,
            1.0,
            "pizza,cake,flamingo,hot dog",
            0,
            1,
            2,
            3,
            4,
            CourierRepository()
        )

        val orders = orderRepository.findByCourierId("courier-2")
        assertTrue(orders.isEmpty())
    }

    @Test
    fun `find by courierId updated orderPriority`() {
        val orderRepository = OrderRepository(
            5.0,
            0.5,
            1.0,
            "pizza,cake,flamingo,hot dog",
            4,
            1,
            2,
            3,
            0,
            CourierRepository()
        )

        val orders = orderRepository.findByCourierId("courier-1")
        assertFalse(orders.isEmpty())
        assertEquals(5, orders.size)

        val firstOrder = orders[3]

        val expectedOrder = Order().withId("order-3")
            .withDescription("Envelope")
            .withFood(false)
            .withVip(true)
            .withPickup(Location(41.37790607439139,2.1801331715968426))
            .withDelivery(Location(41.480661712089115,2.1760928408928155))

        assertEquals(expectedOrder, firstOrder)
    }
}