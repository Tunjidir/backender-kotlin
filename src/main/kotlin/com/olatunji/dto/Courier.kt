package com.olatunji.dto

import com.olatunji.enumeration.Vehicle

class Courier {
    private var id: String = ""
    private var name: String = ""
    private var box: Boolean = false
    private lateinit var vehicle: Vehicle
    private lateinit var location: Location

    fun getId() = id

    fun getName() = name

    fun getBox() = box

    fun getVehicle() = vehicle

    fun getLocation() = location

    fun withId(id: String) : Courier {
        this.id = id
        return this
    }

    fun withName(name: String) : Courier {
        this.name = name
        return this
    }

    fun withBox(box: Boolean) : Courier {
        this.box = box
        return this
    }

    fun withVehicle(vehicle: Vehicle) : Courier {
        this.vehicle = vehicle
        return this;
    }

    fun withLocation(location: Location) : Courier {
        this.location = location
        return this
    }

    override fun equals(other: Any?) : Boolean {
        if(this === other) return false
        if(javaClass != other?.javaClass) return false

        other as Courier
        if(other.id != id) return false
        if(other.name != name) return false
        if(other.box != box) return false
        if(other.vehicle != vehicle) return false
        if(other.location != location) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + box.hashCode()
        result = 31 * result + vehicle.hashCode()
        result = 31 * result + location.hashCode()
        return result
    }

    override fun toString(): String {
        return "Courier{ id = '$id', name = '$name', box = '$box', vehicle = '$vehicle', location = '$location' }"
    }
}