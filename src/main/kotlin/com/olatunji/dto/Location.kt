package com.olatunji.dto

class Location(private var lat: Double, private var lon: Double) {

    fun getLat() = lat

    fun getLon() = lon

    override fun toString() : String {
        return "Location{ longitude = '$lat', latitude = '$lon'}"
    }

    override fun equals(other: Any?) : Boolean {
        if(this === other) return false
        if(javaClass != other?.javaClass) return false

        other as Location
        if(other.lat != lat) return false
        if(other.lon != lon) return false
        return true
    }

    override fun hashCode() : Int {
        val result = 31 * lat.hashCode() + lon.hashCode()
        return result
    }
}