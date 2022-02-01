package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.PlaneSerializer

@Serializable(with = PlaneSerializer::class)
data class Plane(var horizontal: Double, var vertical: Double) {
    operator fun plus(other: Plane): Plane = Plane(horizontal + other.horizontal, vertical + other.vertical)

    operator fun minus(other: Plane): Plane = Plane(horizontal - other.horizontal, vertical - other.vertical)

    operator fun times(other: Plane): Plane = Plane(horizontal * other.horizontal, vertical * other.vertical)

    operator fun div(other: Plane): Plane = Plane(horizontal / other.horizontal, vertical / other.vertical)

}
