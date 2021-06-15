@file:UseSerializers(OutLinerSerializer::class)

package net.lepinoid.bbdatastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.lepinoid.bbdatastructure.serializer.CubeSerializer
import net.lepinoid.bbdatastructure.serializer.OutLinerSerializer
import net.lepinoid.bbdatastructure.util.Direction
import net.lepinoid.bbdatastructure.util.OutLiner
import net.lepinoid.bbdatastructure.util.Vector

@Serializable
data class BBModelData(
    val meta: Meta,
    val name: String,
    @SerialName("geometry_name") val geometryName: String,
    @SerialName("visible_box") val visibleBox: DoubleArray,
    @SerialName("layered_textures") val layeredTextures: Boolean,
    val resolution: Resolution,
    val elements: List<Element>,
    @SerialName("outliner") val outLiner: List<OutLiner>,
    val textures: List<Texture>,
    val animations: List<Animation>
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BBModelData

        if (meta != other.meta) return false
        if (name != other.name) return false
        if (geometryName != other.geometryName) return false
        if (!visibleBox.contentEquals(other.visibleBox)) return false
        if (layeredTextures != other.layeredTextures) return false
        if (resolution != other.resolution) return false
        if (elements != other.elements) return false
        if (outLiner != other.outLiner) return false
        if (textures != other.textures) return false
        if (animations != other.animations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = meta.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + geometryName.hashCode()
        result = 31 * result + visibleBox.contentHashCode()
        result = 31 * result + layeredTextures.hashCode()
        result = 31 * result + resolution.hashCode()
        result = 31 * result + elements.hashCode()
        result = 31 * result + outLiner.hashCode()
        result = 31 * result + textures.hashCode()
        result = 31 * result + animations.hashCode()
        return result
    }
}

@Serializable
data class Meta(
    @SerialName("format_version") val formatVersion: String,
    @SerialName("creation_time") val creationTime: Long,
    @SerialName("model_format") val modelFormat: String,
    @SerialName("box_uv") val boxUv: Boolean
)

@Serializable
data class Resolution(val width: Int, val height: Int)

@Serializable
data class Element(
    val name: String,
    val rescale: Boolean,
    val from: DoubleArray,
    val to: DoubleArray,
    @SerialName("autouv") val autoUv: Int,
    val color: Long,
    @SerialName("locked") val isLocked: Boolean,
    val origin: DoubleArray,
    @SerialName("uv_offset") val uvOffset: IntArray = intArrayOf(0, 0),
    val faces: Map<Direction, ElementFace>,
    /**
     * [Cube.uuid]に対応
     */
    val uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Element

        if (name != other.name) return false
        if (rescale != other.rescale) return false
        if (!from.contentEquals(other.from)) return false
        if (!to.contentEquals(other.to)) return false
        if (autoUv != other.autoUv) return false
        if (color != other.color) return false
        if (isLocked != other.isLocked) return false
        if (!origin.contentEquals(other.origin)) return false
        if (!uvOffset.contentEquals(other.uvOffset)) return false
        if (faces != other.faces) return false
        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + rescale.hashCode()
        result = 31 * result + from.contentHashCode()
        result = 31 * result + to.contentHashCode()
        result = 31 * result + autoUv
        result = 31 * result + color.hashCode()
        result = 31 * result + isLocked.hashCode()
        result = 31 * result + origin.contentHashCode()
        result = 31 * result + uvOffset.contentHashCode()
        result = 31 * result + faces.hashCode()
        result = 31 * result + uuid.hashCode()
        return result
    }
}

@Serializable
data class Group(
    val name: String,
    val origin: DoubleArray,
    val rotation: IntArray = IntArray(3),
    @SerialName("bedrock_binding") val BedrockBinding: String,
    /**
     * [Animation.animators]$keyに対応
     */
    val uuid: String,
    val export: Boolean,
    val isOpen: Boolean,
    val locked: Boolean,
    val visibility: Boolean,
    @SerialName("autouv") val autoUv: Int,
    val children: List<OutLiner>
): OutLiner {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Group

        if (name != other.name) return false
        if (!origin.contentEquals(other.origin)) return false
        if (!rotation.contentEquals(other.rotation)) return false
        if (BedrockBinding != other.BedrockBinding) return false
        if (uuid != other.uuid) return false
        if (export != other.export) return false
        if (isOpen != other.isOpen) return false
        if (locked != other.locked) return false
        if (visibility != other.visibility) return false
        if (autoUv != other.autoUv) return false
        if (children != other.children) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + origin.contentHashCode()
        result = 31 * result + rotation.contentHashCode()
        result = 31 * result + BedrockBinding.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + export.hashCode()
        result = 31 * result + isOpen.hashCode()
        result = 31 * result + locked.hashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + autoUv
        result = 31 * result + children.hashCode()
        return result
    }
}

@Serializable(with = CubeSerializer::class)
data class Cube(
    /**
     * [Element.uuid]に対応
     */
    val uuid: String
    ): OutLiner

@Serializable
data class Texture(
    val path: String,
    val name: String,
    val folder: String,
    val namespace: String,
    val id: String,
    val particle: Boolean,
    val visible: Boolean,
    val mode: String,
    val saved: Boolean,
    val uuid: String,
    val source: String
)

@Serializable
data class Animation(
    val uuid: String,
    val name: String,
    val loop: String,
    val override: Boolean,
    @SerialName("anim_time_update") val animTimeUpdate: String,
    @SerialName("blend_weight") val blendWeight: String,
    val length: Double,
    val snapping: Int,
    val selected: Boolean,
    val saved: Boolean,
    val path: String,
    /**
     * key=[Group.uuid]
     */
    val animators: Map<String, Animator>
)


@Serializable
data class ElementFace(val uv: DoubleArray, val texture: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ElementFace

        if (!uv.contentEquals(other.uv)) return false
        if (texture != other.texture) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uv.contentHashCode()
        result = 31 * result + texture
        return result
    }
}

@Serializable
data class Animator(val name: String, val keyframes: List<Keyframe>)

@Serializable
data class Keyframe(
    val channel: String,
    @SerialName("data_points") val dataPoints: List<Vector>,
    val uuid: String,
    val time: Double,
    val color: Long,
    val interpolation: String
)
