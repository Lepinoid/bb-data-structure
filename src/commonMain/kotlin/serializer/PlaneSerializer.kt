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

object PlaneSerializer : KSerializer<Plane> {
    override fun deserialize(decoder: Decoder): Plane {
        val input = decoder as? JsonDecoder ?: error("can be deserialized only by JSON")
        val json = input.decodeJsonElement().jsonArray
        val horizontal = json[0].jsonPrimitive.content
        val vertical = json[1].jsonPrimitive.content
        return Plane(horizontal.toDouble(), vertical.toDouble())
    }

    override val descriptor: SerialDescriptor
        get() = DoubleArraySerializer().descriptor

    override fun serialize(encoder: Encoder, value: Plane) {
        encoder.beginCollection(descriptor, 2).apply {
            encodeDoubleElement(descriptor, 0, value.horizontal)
            encodeDoubleElement(descriptor, 1, value.vertical)
            endStructure(descriptor)
        }
    }

}