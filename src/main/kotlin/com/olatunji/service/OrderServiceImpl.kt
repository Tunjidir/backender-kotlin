package com.olatunji.service

import com.olatunji.dto.Order
import com.olatunji.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {

    override fun findByCourierId(courierId: String): List<Order> {
        return orderRepository.findByCourierId(courierId)
    }

    override fun findAll(): List<Order> {
        return orderRepository.findAll()
    }
}