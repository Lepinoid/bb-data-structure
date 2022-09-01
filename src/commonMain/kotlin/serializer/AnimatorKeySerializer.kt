package net.lepinoid.bbdatastructure.serializer

import com.benasher44.uuid.uuidFrom
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.lepinoid.bbdatastructure.util.AnimatorKey
import net.lepinoid.uuidserializer.UuidSerializer

object AnimatorKeySerializer : KSerializer<AnimatorKey> {
    private const val EFFECT_KEY = "effects"

    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): AnimatorKey {
        val rawValue = decoder.decodeString()
        return if (rawValue == EFFECT_KEY) {
            AnimatorKey.EffectData
        } else {
            AnimatorKey.GroupId(uuidFrom(rawValue))
        }
    }

    override fun serialize(encoder: Encoder, value: AnimatorKey) {
        when (value) {
            is AnimatorKey.EffectData -> encoder.encodeString(EFFECT_KEY)
            is AnimatorKey.GroupId -> UuidSerializer.serialize(encoder, value.uuid)
        }
    }
}
