package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.lepinoid.bbdatastructure.util.LoopType

object LoopTypeSerializer : KSerializer<LoopType> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): LoopType {
        return LoopType.valueOf(decoder.decodeString().uppercase())
    }

    override fun serialize(encoder: Encoder, value: LoopType) {
        encoder.encodeString(value.toString().lowercase())
    }
}
