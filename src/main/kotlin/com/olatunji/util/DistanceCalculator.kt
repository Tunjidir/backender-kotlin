import com.olatunji.dto.Location

import java.lang.Math.*

/**
 * Shamelessly copied from https://github.com/jasonwinn/haversine
 */
object DistanceCalculator {

    private val EARTH_RADIUS = 6371

    /**
     * Returns distance between two locations in kilometers
     */
    fun calculateDistance(start: Location, end: Location): Double {
        val deltaLat = toRadians(end.getLat() - start.getLat())
        val deltaLong = toRadians(end.getLon() - start.getLon())

        val startLat = toRadians(start.getLat())
        val endLat = toRadians(end.getLat())

        val a = haversin(deltaLat) + cos(startLat) * cos(endLat) * haversin(deltaLong)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS * c
    }

    private fun haversin(value: Double): Double {
        return Math.pow(Math.sin(value / 2), 2.0)
    }
}
