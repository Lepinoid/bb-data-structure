package net.lepinoid.bbdatastructure.serializer

import com.benasher44.uuid.Uuid
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import net.lepinoid.bbdatastructure.Keyframe
import net.lepinoid.bbdatastructure.util.KeyframeData
import net.lepinoid.uuidserializer.UuidSerializer

@Suppress("UNCHECKED_CAST")
object KeyframeSerializer : KSerializer<Keyframe> {
    override fun deserialize(decoder: Decoder): Keyframe {
        decoder.decodeStructure(descriptor) {
            var channel: String? = null
            var list: List<Any>? = null
            var uuid: Uuid? = null
            var time: Double? = null
            var color: Long? = null
            var interpolation: String? = null
            loop@ while (true) {
                when (val i = decodeElementIndex(descriptor)) {
                    DECODE_DONE -> break@loop
                    0 -> channel = decodeStringElement(descriptor, 0)
                    1 -> list = decodeSerializableElement(
                        descriptor,
                        1,
                        ListSerializer(KeyframeDataSerializer.getElementSerializer(channel!!))
                    ) as List<Any>
                    2 -> uuid = decodeSerializableElement(descriptor, 2, UuidSerializer)
                    3 -> time = decodeDoubleElement(descriptor, 3)
                    4 -> color = decodeLongElement(descriptor, 4)
                    5 -> interpolation = decodeStringElement(descriptor, 5)
                    else -> throw SerializationException("Unexpected index $i")
                }
            }
            return Keyframe(
                data = KeyframeData.getConstructor(channel!!, list as List<Any>),
                uuid!!,
                time!!,
                color!!,
                interpolation!!
            )
        }
    }

    override fun serialize(encoder: Encoder, value: Keyframe) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.data.channel)
            encodeSerializableElement(
                descriptor,
                1,
                ListSerializer(KeyframeDataSerializer.getElementSerializer(value.data.channel) as KSerializer<Any>),
                value.data.dataPoints
            )
            encodeSerializableElement(descriptor, 2, UuidSerializer, value.uuid)
            encodeDoubleElement(descriptor, 3, value.time)
            encodeLongElement(descriptor, 4, value.color)
            encodeStringElement(descriptor, 5, value.interpolation)
        }
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("keyframe") {
            element<String>("channel")
            //fake element
            element<String>("data_points")
            element("uuid", UuidSerializer.descriptor)
            element<Double>("time")
            element<Long>("color")
            element<String>("interpolation")
        }
}