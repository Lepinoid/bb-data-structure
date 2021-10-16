package net.lepinoid.bbdatastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.ItemCubeSerializer
import net.lepinoid.bbdatastructure.serializer.vector.ArrayLikeVectorSerializer
import net.lepinoid.bbdatastructure.util.*

/**
 * ItemModelに対応するDataClass
 * 一部Blockbenchからのみ出力される専用のデータにも対応しています
 */
@Serializable
data class ItemModelData(
    var credit: String? = null,
    @SerialName("texture_size")
    var textureSize: Plane? = null,
    var textures: Map<String, String>,
    @SerialName("gui_light")
    var guiLight: String? = null,
    var elements: List<ItemElement>,
    var display: ItemDisplay? = null,
    var groups: List<ItemOutLiner>? = null
)

@Serializable
data class ItemElement(
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var from: Vector,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var to: Vector,
    var rotation: ItemRotation? = null,
    var faces: Map<Direction, ItemElementFace>
)

@Serializable
data class ItemRotation(
    var angle: Double,
    var axis: Axis,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var origin: Vector
)

@Serializable
data class ItemElementFace(
    var uv: UV,
    var rotation: Double? = null,
    var texture: String
)

@Serializable
data class ItemDisplay(
    var fixed: ItemDisplayData? = null,
    @SerialName("firstperson_lefthand")
    var firstPersonLeftHand: ItemDisplayData? = null,
    var gui: ItemDisplayData? = null
)

@Serializable
data class ItemDisplayData(
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var rotation: Vector,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var translation: Vector,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var scale: Vector
)

@Serializable
data class ItemGroup(
    var name: String,
    @Serializable(with = ArrayLikeVectorSerializer::class)
    var origin: Vector,
    var children: Plane
) : ItemOutLiner

@Serializable(with = ItemCubeSerializer::class)
data class ItemCube(val id: Int) : ItemOutLiner
