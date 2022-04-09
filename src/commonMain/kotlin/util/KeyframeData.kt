package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.KeyframeDataSerializer

@Serializable(with = KeyframeDataSerializer::class)
sealed interface KeyframeData {
    val channel: String

    @SerialName("data_points")
    val dataPoints: List<Any>

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getConstructor(channel: String, dataPoints: List<Any>) = when (channel) {
            "rotation" -> Rotation(dataPoints as List<Vector>)
            "position" -> Position(dataPoints as List<Vector>)
            "scale" -> Scale(dataPoints as List<Vector>)
            "particle" -> Particle(dataPoints as List<ParticleData>)
            "sound" -> Sound(dataPoints as List<SoundData>)
            "timeline" -> Instructions(dataPoints as List<InstructionData>)
            else -> error("invalid channel data: $channel")
        }
    }
}

@Serializable
data class Rotation(override val dataPoints: List<Vector>) : KeyframeData {
    override val channel = "rotation"
}

@Serializable
data class Position(override val dataPoints: List<Vector>) : KeyframeData {
    override val channel = "position"
}

@Serializable
data class Scale(override val dataPoints: List<Vector>) : KeyframeData {
    override val channel = "scale"
}

@Serializable
data class Particle(override val dataPoints: List<ParticleData>) : KeyframeData {
    override val channel = "particle"
}

@Serializable
data class Sound(override val dataPoints: List<SoundData>) : KeyframeData {
    override val channel = "sound"
}

@Serializable
data class Instructions(override val dataPoints: List<InstructionData>) : KeyframeData {
    override val channel = "timeline"
}

@Serializable
data class ParticleData(val effect: String, val locator: String, val script: String, val file: String)

@Serializable
data class SoundData(val effect: String, val file: String)

@Serializable
data class InstructionData(val script: String)