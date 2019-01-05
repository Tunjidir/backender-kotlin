package com.olatunji.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.olatunji.dto.Courier
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader

@Component
class CourierRepository {
    private val COURIER_FILE: String  = "/couriers.json"
    private lateinit var courierList: List<Courier>

    init {
        try {
            val reader: Reader = InputStreamReader(OrderRepository::class.javaObjectType.getResourceAsStream(COURIER_FILE))
            courierList = Gson().fromJson(reader)

        } catch(e: IOException) {
            throw RuntimeException(e)
        }
    }

    private inline fun <reified T> Gson.fromJson(json: Reader) = this.fromJson<T>(json, object: TypeToken<T>(){}.type)

    fun findById(courierId: String) : Courier {
        return courierList.filter { courierId == it.getId() }.first()
    }

    fun findAll() : List<Courier> {
        return ArrayList(courierList)
    }
}