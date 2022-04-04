package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.UVSerializer

@Serializable(with = UVSerializer::class)
data class UV(var pos: Plane, var scale: Plane) {
    operator fun plus(other: UV): UV = UV(pos + other.pos, scale + other.scale)

    operator fun minus(other: UV): UV = UV(pos - other.pos, scale - other.scale)

    operator fun times(other: UV): UV = UV(pos * other.pos, scale * other.scale)

    operator fun div(other: UV): UV = UV(pos / other.pos, scale / other.scale)
}
