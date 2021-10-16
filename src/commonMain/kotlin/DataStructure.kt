@file:UseSerializers(UuidSerializer::class)

package net.lepinoid.bbdatastructure

import com.benasher44.uuid.Uuid
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.lepinoid.bbdatastructure.serializer.BBCubeSerializer
import net.lepinoid.bbdatastructure.serializer.vector.ArrayLikeVectorSerializer
import net.lepinoid.bbdatastructure.util.*
import net.lepinoid.uuidserializer.UuidSerializer

@Serializable
data class BBModelData(
    var meta: BBMeta,
    var name: String,
    @SerialName("geometry_name")
    var geometryName: String,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    @SerialName("visible_box")
    var visibleBox: Vector,
    @SerialName("layered_textures")
    var layeredTextures: Boolean = false,
    var resolution: Resolution,
    var flag: String? = null,
    var elements: List<BBElement>,
    @SerialName("outliner")
    var outLiner: List<BBOutLiner>,
    var textures: List<BBTexture>,
    var animations: List<BBAnimation>,
    @SerialName("animation_variable_placeholders")
    var animationVariablePlaceholders: String? = null
)

@Serializable
data class BBMeta(
    @SerialName("format_version")
    var formatVersion: String,
    @SerialName("creation_time")
    var creationTime: Long,
    var backup: Boolean? = null,
    @SerialName("model_format")
    var modelFormat: String,
    @SerialName("box_uv")
    var boxUv: Boolean
)

@Serializable
data class Resolution(var width: Int, var height: Int)

@Serializable
data class BBElementCube(
    override var name: String,
    var rescale: Boolean,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    override var from: Vector,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var to: Vector,
    @SerialName("autouv")
    var autoUv: Int,
    var color: Long,
    @SerialName("locked")
    override var isLocked: Boolean,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var rotation: Vector = Vector(0.0, 0.0, 0.0),
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var origin: Vector,
    @SerialName("uv_offset")
    var uvOffset: Plane? = null,
    var faces: Map<Direction, BBElementFace>,
    /**
     * [BBCube.uuid]に対応
     */
    override var uuid: Uuid,
    override var type: String? = null
) : BBElement

@Serializable
data class BBElementLocator(
    override var name: String,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    override var from: Vector,
    @SerialName("locked")
    override var isLocked: Boolean,
    override var uuid: Uuid,
    override var type: String?
) : BBElement

@Serializable
data class BBGroup(
    var name: String,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var origin: Vector,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var rotation: Vector? = null,
    @SerialName("bedrock_binding")
    var BedrockBinding: String,
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
) : BBOutLiner

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
    @SerialName("old_width")
    var oldWidth: Int? = null,
    @SerialName("old_height")
    var oldHeight: Int? = null,
    @SerialName("relative_path")
    var relativePath: String? = null,
    var source: String,
)

@Serializable
data class BBAnimation(
    var uuid: Uuid,
    var name: String,
    var loop: String,
    var override: Boolean,
    @SerialName("anim_time_update")
    var animTimeUpdate: String,
    @SerialName("blend_weight")
    var blendWeight: String,
    @SerialName("start_delay")
    var startDelay: String = "",
    @SerialName("loop_delay")
    var loopDelay: String = "",
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
data class BBElementFace(var uv: UV, var texture: Int)

@Serializable
data class BBAnimator(var name: String, var keyframes: List<Keyframe>)

@Serializable
data class Keyframe(
    var channel: String,
    @SerialName("data_points")
    var dataPoints: List<Vector>,
    var uuid: Uuid,
    var time: Double,
    var color: Long,
    var interpolation: String
)
