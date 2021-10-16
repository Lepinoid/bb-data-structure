package net.lepinoid.bbdatastructure.serializer.vector

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.lepinoid.bbdatastructure.util.Vector

/**
 * BlockBenchから出力されるものはString型とDouble(Int)型が混在する
 */
object VectorSerializer: KSerializer<Vector> {
    override fun deserialize(decoder: Decoder): Vector {
        val input = decoder as? JsonDecoder ?: error("can be deserialized only by JSON")
        val json = input.decodeJsonElement().jsonObject
        val x = json.getValue("x").jsonPrimitive.content
        val y = json.getValue("y").jsonPrimitive.content
        val z = json.getValue("z").jsonPrimitive.content
        return Vector(x.toDouble(), y.toDouble(), z.toDouble())
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("Vector") {
            element<JsonPrimitive>("x")
            element<JsonPrimitive>("y")
            element<JsonPrimitive>("z")
        }

    override fun serialize(encoder: Encoder, value: Vector) {
        encoder.beginStructure(descriptor).apply {
            encodeDoubleElement(descriptor, 0, value.x)
            encodeDoubleElement(descriptor, 1, value.y)
            encodeDoubleElement(descriptor, 2, value.z)
            endStructure(descriptor)
        }
    }

}