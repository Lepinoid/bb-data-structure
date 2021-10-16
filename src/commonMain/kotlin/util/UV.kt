package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.UVSerializer

@Serializable(with = UVSerializer::class)
data class UV(var start: Plane, var end: Plane)
