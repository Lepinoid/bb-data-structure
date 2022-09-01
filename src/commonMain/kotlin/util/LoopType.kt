package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LoopType {
    @SerialName("once")
    ONCE,

    @SerialName("loop")
    LOOP,

    @SerialName("hold")
    HOLD
}
