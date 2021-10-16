package net.lepinoid.bbdatastructure.serializer.vector

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.DoubleArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import net.lepinoid.bbdatastructure.util.Vector

object ArrayLikeVectorSerializer : KSerializer<Vector> {
    override fun deserialize(decoder: Decoder): Vector {
        val input = decoder as? JsonDecoder ?: error("can be deserialized only by JSON")
        val json = input.decodeJsonElement().jsonArray
        val x = json[0].jsonPrimitive.content
        val y = json[1].jsonPrimitive.content
        val z = json[2].jsonPrimitive.content
        return Vector(x.toDouble(), y.toDouble(), z.toDouble())
    }

    override val descriptor: SerialDescriptor
        get() = DoubleArraySerializer().descriptor

    override fun serialize(encoder: Encoder, value: Vector) {
        encoder.beginCollection(descriptor, 3).apply {
            encodeDoubleElement(descriptor, 0, value.x)
            encodeDoubleElement(descriptor, 1, value.y)
            encodeDoubleElement(descriptor, 2, value.z)
            endStructure(descriptor)
        }
    }
}