package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable

@Serializable
data class Button(
    val type: String,
    val id: String,
    val value: Int,
    val variable: String? = null,
    val duration: Float? = null,
    val step: Float? = null,
    val min: Float? = null,
    val max: Float? = null
)
