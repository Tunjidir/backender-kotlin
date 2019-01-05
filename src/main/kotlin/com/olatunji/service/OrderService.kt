package com.olatunji.service

import com.olatunji.dto.Order

interface OrderService {

    fun findAll() : List<Order>

    fun findByCourierId(courierId: String) : List<Order>

}