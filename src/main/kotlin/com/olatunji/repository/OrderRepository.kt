package com.olatunji.repository

import DistanceCalculator.calculateDistance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.olatunji.enumeration.Vehicle
import com.olatunji.dto.Courier
import com.olatunji.dto.Location
import com.olatunji.dto.Order
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader

@Component
class OrderRepository(
    @Value("\${courier.deliver.long.distance}")
    private val longDeliveryDistance: Double,
    @Value("\${courier.pickup.short.distance}")
    private val shortPickupDistance: Double,
    @Value("\${courier.pickup.long.distance}")
    private val longPickupDistance: Double,
    @Value("\${glovo.box.words}")
    private val glovoBoxWords: String,
    @Value("\${short.pickup.distance.order.priority}")
    private val shortPickupDistanceOrderPriority: Int,
    @Value("\${long.pickup.distance.order.priority}")
    private val longPickupDistanceOrderPriority: Int,
    @Value("\${vip.order.priority}")
    private val vipOrderPriority: Int,
    @Value("\${food.order.priority}")
    private val foodOrderPriority: Int,
    @Value("\${others.order.priority}")
    private val othersOrderPriority: Int,
    private val courierRepository: CourierRepository
){
    private val ORDER_FILE: String = "/orders.json"
    private lateinit var orderList: List<Order>

    init {
        try {
            val reader: Reader = InputStreamReader(OrderRepository::class.javaObjectType.getResourceAsStream(ORDER_FILE))
            orderList = Gson().fromJson(reader)

        } catch(e: IOException) {
            throw RuntimeException(e)
        }
    }

    private inline fun <reified T> Gson.fromJson(json: Reader) = this.fromJson<T>(json, object: TypeToken<T>(){}.type)

    fun findByCourierId(courierId: String) : List<Order> {
        val courier = courierRepository.findById(courierId)
        val location = courier.getLocation()

        val filteredOrders = findFilteredOrder(courier)
        val shortPickupDistanceOrders = getShortPickupDistanceOrders(filteredOrders, location)
        val longPickupDistanceOrders = getLongPickupDistanceOrders(filteredOrders, location)
        val vipOrders = getVipOrders(filteredOrders, location)
        val foodOrders = getFoodOrders(filteredOrders, location)
        val otherOrders = getOtherOrders(filteredOrders, location)
        val orderPriorities = getOrderPriorities(shortPickupDistanceOrders, longPickupDistanceOrders, vipOrders, foodOrders, otherOrders)

        return ArrayList(orderPriorities)
    }

    fun findAll() : List<Order> {
        return ArrayList(orderList)
    }

    private fun findFilteredOrder(courier: Courier) : List<Order> {
        val glovoBoxKeyWords = glovoBoxWords.toLowerCase().split(",")
        val allowedVehicles = listOf(Vehicle.ELECTRIC_SCOOTER, Vehicle.MOTORCYCLE)

        return orderList.filter { order ->
            val description = order.getDescription().toLowerCase()
            courier.getBox() || glovoBoxKeyWords.none{ it in description}
        }.filter {order ->
            val pickupLocation = order.getPickup()
            val deliveryLocation = order.getDelivery()
            val distance = calculateDistance(deliveryLocation, pickupLocation)

            (distance <= longDeliveryDistance) || (courier.getVehicle() in allowedVehicles)
        }
    }

    private fun getLongPickupDistanceOrders(filteredOrders: List<Order>, courierLocation: Location) : List<Order> {
        return filteredOrders.filter {
            val pickupLocation = it.getPickup()
            val distance = calculateDistance(courierLocation, pickupLocation)

            (distance > shortPickupDistance) && (distance <= longPickupDistance)
        }
    }

    private fun getShortPickupDistanceOrders(filteredOrders: List<Order>, courierLocation: Location) : List<Order> {
        return filteredOrders.filter {
            val pickupLocation = it.getPickup()
            val distance = calculateDistance(courierLocation, pickupLocation)

            distance <= shortPickupDistance
        }
    }

    private fun getVipOrders(filteredOrders: List<Order>, courierLocation: Location) : List<Order> {
        return filteredOrders.filter {
            val pickupLocation = it.getPickup()
            val distance = calculateDistance(courierLocation, pickupLocation)

            (distance > longPickupDistance) && (it.getVip())
        }
    }

    private fun getFoodOrders(filteredOrders: List<Order>, courierLocation: Location) : List<Order> {
        return filteredOrders.filter {
            val pickupLocation = it.getPickup()
            val distance = calculateDistance(courierLocation, pickupLocation)

            (distance > longPickupDistance) && (!it.getVip()) && (!it.getFood())
        }
    }

    private fun getOtherOrders(filteredOrders: List<Order>, courierLocation: Location) : List<Order> {
        return filteredOrders.filter {
            val pickupLocation = it.getPickup()
            val distance = calculateDistance(courierLocation, pickupLocation)

            (distance > longPickupDistance) && (!it.getVip()) && (it.getFood())
        }
    }

    private fun getOrderPriorities(shortPickupDistanceOrders: List<Order>, longPickupDistanceOrders: List<Order>,
                                   vipOrders: List<Order>, foodOrders: List<Order>, otherOrders: List<Order>) : List<Order> {
        val priorityList = ArrayList<Order>()
        val priorityMap = HashMap<Int, List<Order>>()
        priorityMap.put(shortPickupDistanceOrderPriority, shortPickupDistanceOrders)
        priorityMap.put(longPickupDistanceOrderPriority, longPickupDistanceOrders)
        priorityMap.put(vipOrderPriority, vipOrders)
        priorityMap.put(foodOrderPriority, foodOrders)
        priorityMap.put(othersOrderPriority, otherOrders)

        priorityMap.keys.forEach { it -> priorityList.addAll(priorityMap[it]!!) }

        return priorityList
    }
}