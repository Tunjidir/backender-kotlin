package com.olatunji.controller

import com.olatunji.dto.Order
import com.olatunji.service.OrderService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
class OrderEndpoint(
    @Value("\${backender.welcome_message}")
    private val message: String,
    private val orderService: OrderService
) {

    @RequestMapping("/welcome")
    fun root() : String {
        return message
    }

    @RequestMapping("/orders")
    fun findAllOrders() : ResponseEntity<List<Order>> {
       val orders =  orderService.findAll()
        return ResponseEntity.ok(orders)
    }

    @RequestMapping("/orders/{courierId}")
    fun findOrdersById(@PathVariable courierId: String) : ResponseEntity<List<Order>> {
        val ordersById =  orderService.findByCourierId(courierId)
        return ResponseEntity.ok(ordersById)
    }
}