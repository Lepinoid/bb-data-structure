@file:UseSerializers(BBElementSerializer::class, BBOutLinerSerializer::class, UuidSerializer::class)

package net.lepinoid.bbdatastructure

import com.benasher44.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.lepinoid.bbdatastructure.serializer.BBCubeSerializer
import net.lepinoid.bbdatastructure.serializer.BBElementSerializer
import net.lepinoid.bbdatastructure.serializer.BBOutLinerSerializer
import net.lepinoid.bbdatastructure.util.BBElement
import net.lepinoid.bbdatastructure.util.Direction
import net.lepinoid.bbdatastructure.util.BBOutLiner
import net.lepinoid.bbdatastructure.util.Vector
import net.lepinoid.uuidserializer.UuidSerializer

@Serializable
data class BBModelData(
    var meta: BBMeta,
    var name: String,
    @SerialName("geometry_name") var geometryName: String,
    @SerialName("visible_box") var visibleBox: DoubleArray,
    @SerialName("layered_textures") var layeredTextures: Boolean = false,
    var resolution: Resolution,
    var flag: String? = null,
    var elements: List<BBElement>,
    @SerialName("outliner") var outLiner: List<BBOutLiner>,
    var textures: List<BBTexture>,
    var animations: List<BBAnimation>,
    @SerialName("animation_variable_placeholders") var animationVariablePlaceholders: String? = null
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
data class BBMeta(
    @SerialName("format_version") var formatVersion: String,
    @SerialName("creation_time") var creationTime: Long,
    var backup: Boolean? = null,
    @SerialName("model_format") var modelFormat: String,
    @SerialName("box_uv") var boxUv: Boolean
)

@Serializable
data class Resolution(var width: Int, var height: Int)

@Serializable
data class BBElementCube(
    override var name: String,
    var rescale: Boolean,
    override var from: DoubleArray,
    var to: DoubleArray,
    @SerialName("autouv") var autoUv: Int,
    var color: Long,
    @SerialName("locked") override var isLocked: Boolean,
    var rotation: DoubleArray = doubleArrayOf(0.0, 0.0, 0.0),
    var origin: DoubleArray,
    @SerialName("uv_offset") var uvOffset: IntArray? = null,
    var faces: Map<Direction, BBElementFace>,
    /**
     * [BBCube.uuid]に対応
     */
    override var uuid: Uuid,
    override var type: String? = null
): BBElement {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BBElementCube

        if (name != other.name) return false
        if (rescale != other.rescale) return false
        if (!from.contentEquals(other.from)) return false
        if (!to.contentEquals(other.to)) return false
        if (autoUv != other.autoUv) return false
        if (color != other.color) return false
        if (isLocked != other.isLocked) return false
        if (!rotation.contentEquals(other.rotation)) return false
        if (!origin.contentEquals(other.origin)) return false
        if (uvOffset != null) {
            if (other.uvOffset == null) return false
            if (!uvOffset.contentEquals(other.uvOffset)) return false
        } else if (other.uvOffset != null) return false
        if (faces != other.faces) return false
        if (uuid != other.uuid) return false
        if (type != other.type) return false

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
        result = 31 * result + rotation.contentHashCode()
        result = 31 * result + origin.contentHashCode()
        result = 31 * result + (uvOffset?.contentHashCode() ?: 0)
        result = 31 * result + faces.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        return result
    }


}

@Serializable
data class BBElementLocator(
    override var name: String,
    override var from: DoubleArray,
    @SerialName("locked") override var isLocked: Boolean,
    override var uuid: Uuid,
    override var type: String?
): BBElement {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BBElementLocator

        if (name != other.name) return false
        if (!from.contentEquals(other.from)) return false
        if (isLocked != other.isLocked) return false
        if (uuid != other.uuid) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + from.contentHashCode()
        result = 31 * result + isLocked.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + (type?.hashCode() ?: 0)
        return result
    }
}

@Serializable
data class BBGroup(
    var name: String,
    var origin: DoubleArray,
    var rotation: IntArray? = null,
    @SerialName("bedrock_binding") var BedrockBinding: String,
    var color: Int = 0,
    /**
     * [BBAnimation.animators]$keyに対応
     */
    var uuid: Uuid,
    var export: Boolean,
    var isOpen: Boolean,
    var locked: Boolean,
    var visibility: Boolean,
    @SerialName("autouv") var autoUv: Int,
    var children: List<BBOutLiner>
) : BBOutLiner {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BBGroup

        if (name != other.name) return false
        if (!origin.contentEquals(other.origin)) return false
        if (rotation != null) {
            if (other.rotation == null) return false
            if (!rotation.contentEquals(other.rotation)) return false
        } else if (other.rotation != null) return false
        if (BedrockBinding != other.BedrockBinding) return false
        if (color != other.color) return false
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
        result = 31 * result + (rotation?.contentHashCode() ?: 0)
        result = 31 * result + BedrockBinding.hashCode()
        result = 31 * result + color
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

@Serializable(with = BBCubeSerializer::class)
data class BBCube(
    /**
     * [BBElement.uuid]に対応
     */
    var uuid: Uuid
) : BBOutLiner

@Serializable
data class BBTexture(
    var path: String,
    var name: String,
    var folder: String,
    var namespace: String,
    var id: String,
    var particle: Boolean,
    @SerialName("render_mode")
    var renderMode: String = "",
    var visible: Boolean,
    var mode: String,
    var saved: Boolean,
    var uuid: Uuid,
    @SerialName("old_width") var oldWidth: Int? = null,
    @SerialName("old_height") var oldHeight: Int? = null,
    @SerialName("relative_path") var relativePath: String? = null,
    var source: String,
)

@Serializable
data class BBAnimation(
    var uuid: Uuid,
    var name: String,
    var loop: String,
    var override: Boolean,
    @SerialName("anim_time_update") var animTimeUpdate: String,
    @SerialName("blend_weight") var blendWeight: String,
    @SerialName("start_delay") var startDelay: String = "",
    @SerialName("loop_delay") var loopDelay: String = "",
    var length: Double,
    var snapping: Int,
    var selected: Boolean,
    var saved: Boolean,
    var path: String? = null,
    /**
     * key=[BBGroup.uuid]
     */
    var animators: Map<Uuid, BBAnimator>
)


@Serializable
data class BBElementFace(var uv: DoubleArray, var texture: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BBElementFace

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
data class BBAnimator(var name: String, var keyframes: List<Keyframe>)

@Serializable
data class Keyframe(
    var channel: String,
    @SerialName("data_points") var dataPoints: List<Vector>,
    var uuid: Uuid,
    var time: Double,
    var color: Long,
    var interpolation: String
)
