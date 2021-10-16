package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Positions {
    /**
     * refers to item frames
     */
    @SerialName("fixed")
    FIXED,

    @SerialName("gui")
    GUI,

    @SerialName("ground")
    GROUND,

    @SerialName("head")
    HEAD,

    @SerialName("thirdperson_righthand")
    THIRDPERSON_RIGHTHAND,

    @SerialName("thirdperson_lefthand")
    THIRDPERSON_LEFTHAND,

    @SerialName("firstperson_righthand")
    FIRSTPERSON_RIGHTHAND,

    @SerialName("firstperson_lefthand")
    FIRSTPERSON_LEFTHAND,
}