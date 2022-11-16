@file:UseSerializers(UuidSerializer::class)

package net.lepinoid.bbdatastructure.serializer

import com.benasher44.uuid.Uuid
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.lepinoid.bbdatastructure.BBElementCube
import net.lepinoid.bbdatastructure.BBElementFace
import net.lepinoid.bbdatastructure.BBElementLocator
import net.lepinoid.bbdatastructure.util.BBElement
import net.lepinoid.bbdatastructure.util.Direction
import net.lepinoid.bbdatastructure.util.Plane
import net.lepinoid.bbdatastructure.util.Vector
import net.lepinoid.uuidserializer.UuidSerializer

object BBElementSerializer : KSerializer<BBElement> {
    override val descriptor: SerialDescriptor = serialDescriptor<BBElement>()

    override fun deserialize(decoder: Decoder): BBElement {
        val rawData = BBElementSurrogate.serializer().deserialize(decoder)
        when (rawData.type) {
            "cube" -> rawData.run {
                BBElementCube(
                    name,
                    rescale!!,
                    from,
                    to!!,
                    autoUv!!,
                    color!!,
                    isLocked,
                    rotation!!,
                    origin!!,
                    uvOffset!!,
                    faces!!,
                    uuid,
                    type
                )
            }

            "locator" -> rawData.run {
                BBElementLocator(name, from, isLocked, uuid, type)
            }
        }
        error("Cannot serialize elements. [type] shows incompatible data: ${rawData.type}")
    }

    override fun serialize(encoder: Encoder, value: BBElement) {
        when (value) {
            is BBElementCube -> BBElementCube.serializer().serialize(encoder, value)
            is BBElementLocator -> BBElementLocator.serializer().serialize(encoder, value)
        }
    }

    @Serializable
    private class BBElementSurrogate(
        val name: String,
        val from: Vector,
        @SerialName("locked")
        val isLocked: Boolean,
        val uuid: Uuid,
        val type: String = "cube",
        // Cube
        val rescale: Boolean? = null,
        val to: Vector? = null,
        @SerialName("autouv")
        val autoUv: Int? = null,
        val color: Long? = null,
        val rotation: Vector? = null,
        val origin: Vector? = null,
        @SerialName("uv_offset")
        val uvOffset: Plane? = null,
        val faces: Map<Direction, BBElementFace>? = null
    )
}
