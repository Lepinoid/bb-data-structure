package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.UVSerializer

@Serializable(with = UVSerializer::class)
data class UV(var start: Plane, var end: Plane) {
    operator fun plus(other: UV): UV = UV(start + other.start, end + other.end)

    operator fun minus(other: UV): UV = UV(start - other.start, end - other.end)

    operator fun times(other: UV): UV = UV(start * other.start, end * other.end)

    operator fun div(other: UV): UV = UV(start / other.start, end / other.end)
}
