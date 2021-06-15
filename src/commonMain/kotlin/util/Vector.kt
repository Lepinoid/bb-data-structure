package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.VectorSerializer

@Serializable(with = VectorSerializer::class)
data class Vector(val x: Double, val y: Double, val z: Double)
