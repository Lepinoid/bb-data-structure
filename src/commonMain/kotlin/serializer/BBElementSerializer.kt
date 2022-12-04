@file:UseSerializers(UuidSerializer::class)

package net.lepinoid.bbdatastructure.serializer

import com.benasher44.uuid.Uuid
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.lepinoid.bbdatastructure.BBElementCube
import net.lepinoid.bbdatastructure.BBElementFace
import net.lepinoid.bbdatastructure.BBElementLocator
import net.lepinoid.bbdatastructure.serializer.vector.ArrayLikeVectorSerializer
import net.lepinoid.bbdatastructure.util.BBElement
import net.lepinoid.bbdatastructure.util.Direction
import net.lepinoid.bbdatastructure.util.Plane
import net.lepinoid.bbdatastructure.util.Vector
import net.lepinoid.uuidserializer.UuidSerializer

object BBElementSerializer : KSerializer<BBElement> {
    override val descriptor: SerialDescriptor = BBElementSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): BBElement {
        val rawData = BBElementSurrogate.serializer().deserialize(decoder)
        return when (rawData.type) {
            "cube" -> rawData.run {
                BBElementCube(
                    name,
                    rescale!!,
                    from,
                    to!!,
                    autoUv!!,
                    color!!,
                    isLocked,
                    rotation,
                    origin!!,
                    uvOffset,
                    faces!!,
                    uuid,
                    type,
                    boxUv ?: true
                )
            }

            "locator" -> rawData.run {
                if (boxUv != null) {
                    // TODO use logger system
                    println("boxUv exists in BBElementLocator data")
                }
                BBElementLocator(name, from, isLocked, uuid, type)
            }

            else -> error("Cannot serialize elements. [type] shows incompatible data: ${rawData.type}")
        }

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
        @Serializable(with = ArrayLikeVectorSerializer::class)
        val from: Vector,
        @SerialName("locked")
        val isLocked: Boolean,
        val uuid: Uuid,
        // bb:v4.5.1では確実にtypeが設定されるが、過去バージョンではcubeの場合に空欄となっている
        val type: String = "cube",
        // bb:v4.5.1にてCubeで確認、他オブジェクトでも存在するかも不明
        @SerialName("box_uv")
        val boxUv: Boolean? = null,
        // Cube
        val rescale: Boolean? = null,
        @Serializable(with = ArrayLikeVectorSerializer::class)
        val to: Vector? = null,
        @SerialName("autouv")
        val autoUv: Int? = null,
        val color: Long? = null,
        @Serializable(with = ArrayLikeVectorSerializer::class)
        val rotation: Vector = Vector.ZERO,
        @Serializable(with = ArrayLikeVectorSerializer::class)
        val origin: Vector? = null,
        @SerialName("uv_offset")
        val uvOffset: Plane? = null,
        val faces: Map<Direction, BBElementFace>? = null
    )
}
