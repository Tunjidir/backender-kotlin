package com.olatunji.dto

class Order {
    private var id: String = ""
    private var description: String = ""
    private var food: Boolean = false
    private var vip: Boolean = false
    private lateinit var pickup: Location
    private lateinit var delivery: Location

    fun getId() = id

    fun withId(id: String) : Order {
        this.id = id
        return this
    }

    fun getDescription() = description

    fun withDescription(desc: String) : Order {
        this.description = desc
        return this
    }

    fun getFood() = food

    fun withFood(food: Boolean) : Order {
        this.food = food
        return this
    }

    fun getVip() = vip

    fun withVip(vip: Boolean) : Order {
        this.vip = vip
        return this
    }

    fun getPickup() = pickup

    fun withPickup(pickup: Location) : Order {
        this.pickup = pickup
        return this
    }

    fun getDelivery() = delivery

    fun withDelivery(delivery: Location) : Order {
        this.delivery = delivery
        return this
    }

    override fun equals(other: Any?) : Boolean {
        if(this === other) return false
        if(javaClass != other?.javaClass) return false

        other as Order
        if(other.id != id) return false
        if(other.description != description) return false
        if(other.vip != vip) return false
        if(other.food != food) return false
        if(other.pickup != pickup) return false
        if(other.delivery != delivery) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + vip.hashCode()
        result = 31 * result + food.hashCode()
        result = 31 * result + pickup.hashCode()
        result = 31 * result + delivery.hashCode()
        return result
    }

    override fun toString(): String {
        return "Order{ id = '$id', description = '$description', vip = '$vip', food = '$food'" +
                " pickup = '$pickup', delivery = '$delivery'}"
    }
}