package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.lepinoid.bbdatastructure.serializer.vector.VectorSerializer
import net.lepinoid.bbdatastructure.util.InstructionData
import net.lepinoid.bbdatastructure.util.KeyframeData
import net.lepinoid.bbdatastructure.util.ParticleData
import net.lepinoid.bbdatastructure.util.SoundData


object KeyframeDataSerializer : JsonContentPolymorphicSerializer<KeyframeData>(KeyframeData::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out KeyframeData> =
        object : AbstractKeyframeDataSerializer() {
            override val elementSerializer: KSerializer<*> =
                getElementSerializer(element.jsonObject["channel"]!!.jsonPrimitive.content)
        }

    fun getElementSerializer(channel: String): KSerializer<*> = when (channel) {
        "rotation", "position", "scale" -> VectorSerializer
        "particle" -> ParticleData.serializer()
        "sound" -> SoundData.serializer()
        "timeline" -> InstructionData.serializer()
        else -> error("Invalid channel name $channel")
    }

}


@Suppress("UNCHECKED_CAST")
abstract class AbstractKeyframeDataSerializer : KSerializer<KeyframeData> {
    override fun serialize(encoder: Encoder, value: KeyframeData) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.channel)
            encodeSerializableElement(
                descriptor,
                1,
                ListSerializer(elementSerializer) as KSerializer<List<Any>>,
                value.dataPoints
            )
        }
    }

    override fun deserialize(decoder: Decoder): KeyframeData = decoder.decodeStructure(descriptor) {
        val channel = decodeStringElement(descriptor, 0)
        val list = decodeSerializableElement(descriptor, 1, ListSerializer(elementSerializer))
        return KeyframeData.getConstructor(channel, list as List<Any>)
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("keyframedata") {
            element("channel", String.serializer().descriptor)
            element("data_points", ListSerializer(elementSerializer).descriptor)
        }

    abstract val elementSerializer: KSerializer<*>

}