package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import net.lepinoid.bbdatastructure.BBCube
import net.lepinoid.bbdatastructure.BBGroup
import net.lepinoid.bbdatastructure.util.BBElement
import net.lepinoid.bbdatastructure.util.BBOutLiner
import net.lepinoid.uuidserializer.UuidSerializer

/**
 * BlockBenchから出力されるものは[BBCube]のUuidのみ([BBElement.uuid]と対応)と[BBGroup]の二種類があるため，適切な判別/元通りの出力を行うために作成
 */
object BBOutLinerSerializer: JsonContentPolymorphicSerializer<BBOutLiner>(BBOutLiner::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out BBOutLiner> = when (element) {
        is JsonPrimitive -> BBCubeSerializer
        else -> BBGroup.serializer()
    }
}

object BBCubeSerializer: KSerializer<BBCube> {
    override fun deserialize(decoder: Decoder): BBCube = BBCube(decoder.decodeSerializableValue(UuidSerializer))

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("BBCube", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BBCube) {
        encoder.encodeSerializableValue(UuidSerializer, value.uuid)
    }

}