package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.PlaneSerializer

@Serializable(with = PlaneSerializer::class)
data class Plane(var horizontal: Double, var vertical: Double)
