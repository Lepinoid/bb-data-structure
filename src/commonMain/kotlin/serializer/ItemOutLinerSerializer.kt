package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import net.lepinoid.bbdatastructure.ItemCube
import net.lepinoid.bbdatastructure.ItemGroup
import net.lepinoid.bbdatastructure.ItemModelData
import net.lepinoid.bbdatastructure.util.ItemOutLiner


/**
 * BlockBenchから出力されるものは[ItemCube]のidのみ([ItemModelData.elements]のindexと対応)と[ItemGroup]の二種類があるため，適切な判別/元通りの出力を行うために作成
 */
object ItemOutLinerSerializer : JsonContentPolymorphicSerializer<ItemOutLiner>(ItemOutLiner::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out ItemOutLiner> = when (element) {
        is JsonPrimitive -> ItemCubeSerializer
        else -> ItemGroup.serializer()
    }
}

object ItemCubeSerializer : KSerializer<ItemCube> {
    override fun deserialize(decoder: Decoder): ItemCube = ItemCube(decoder.decodeInt())

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ItemCube", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: ItemCube) {
        encoder.encodeInt(value.id)
    }

}