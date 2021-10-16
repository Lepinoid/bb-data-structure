package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.vector.VectorSerializer

@Serializable(with = VectorSerializer::class)
data class Vector(var x: Double, var y: Double, var z: Double)
