package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.DoubleArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import net.lepinoid.bbdatastructure.util.Plane
import net.lepinoid.bbdatastructure.util.UV

object UVSerializer : KSerializer<UV> {
    override fun deserialize(decoder: Decoder): UV {
        val input = decoder as? JsonDecoder ?: error("can be deserialized only by JSON")
        val json = input.decodeJsonElement().jsonArray
        val startHorizontal = json[0].jsonPrimitive.content.toDouble()
        val startVertical = json[1].jsonPrimitive.content.toDouble()
        val endHorizontal = json[2].jsonPrimitive.content.toDouble()
        val endVertical = json[3].jsonPrimitive.content.toDouble()
        return UV(Plane(startHorizontal, startVertical), Plane(endHorizontal, endVertical))
    }

    override val descriptor: SerialDescriptor
        get() = DoubleArraySerializer().descriptor

    override fun serialize(encoder: Encoder, value: UV) {
        encoder.beginCollection(descriptor, 4).apply {
            encodeDoubleElement(descriptor, 0, value.start.horizontal)
            encodeDoubleElement(descriptor, 1, value.start.vertical)
            encodeDoubleElement(descriptor, 2, value.end.horizontal)
            encodeDoubleElement(descriptor, 3, value.end.vertical)
            endStructure(descriptor)
        }
    }

}