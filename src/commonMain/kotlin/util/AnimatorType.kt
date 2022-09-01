package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AnimatorType {
    @SerialName("bone")
    BONE,

    @SerialName("effect")
    EFFECT
}
