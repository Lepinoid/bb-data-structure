package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import net.lepinoid.bbdatastructure.Cube
import net.lepinoid.bbdatastructure.Group
import net.lepinoid.bbdatastructure.util.OutLiner

/**
 * BlockBenchから出力されるものはCubeのUuidのみ(ElementのUuidと対応)とGroupの二種類があるため，適切な判別/元通りの出力を行うために作成
 */
object OutLinerSerializer: JsonContentPolymorphicSerializer<OutLiner>(OutLiner::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out OutLiner> = when {
        element is JsonPrimitive -> CubeSerializer
        else -> Group.serializer()
    }
}

object CubeSerializer: KSerializer<Cube> {
    override fun deserialize(decoder: Decoder): Cube = Cube(decoder.decodeString())

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Cube", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Cube) {
        encoder.encodeString(value.uuid)
    }

}