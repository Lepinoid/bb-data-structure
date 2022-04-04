package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.vector.VectorSerializer
import kotlin.math.sqrt

@Serializable(with = VectorSerializer::class)
data class Vector(val x: Double, val y: Double, val z: Double) {
    operator fun plus(other: Vector): Vector = Vector(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector): Vector = Vector(x - other.x, y - other.y, z - other.z)

    operator fun times(other: Vector): Vector = Vector(x * other.x, y * other.y, z * other.z)

    operator fun div(other: Vector): Vector = Vector(x / other.x, y / other.y, z / other.z)

    operator fun plus(value: Double): Vector = Vector(x + value, y + value, z + value)

    operator fun minus(value: Double): Vector = Vector(x - value, y - value, z - value)

    operator fun times(value: Double): Vector = Vector(x * value, y * value, z * value)

    operator fun div(value: Double): Vector = Vector(x / value, y / value, z / value)

    val lengthSquared: Double
        get() = x * x + y * y + z * z

    @Suppress("UNUSED")
    val length: Double
        get() = sqrt(lengthSquared)

    companion object {
        val ZERO = Vector(0.0, 0.0, 0.0)
    }
}