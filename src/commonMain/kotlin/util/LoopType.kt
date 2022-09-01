package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.LoopTypeSerializer

@Serializable(LoopTypeSerializer::class)
enum class LoopType {
    ONCE, LOOP, HOLD
}
