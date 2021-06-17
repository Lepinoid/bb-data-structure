@file:UseSerializers(ItemOutLinerSerializer::class)
package net.lepinoid.bbdatastructure

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.lepinoid.bbdatastructure.serializer.ItemCubeSerializer
import net.lepinoid.bbdatastructure.serializer.ItemOutLinerSerializer
import net.lepinoid.bbdatastructure.util.Axis
import net.lepinoid.bbdatastructure.util.Direction
import net.lepinoid.bbdatastructure.util.ItemOutLiner

@Serializable
data class ItemModelData(
    var credit: String,
    var textures: Map<String, String>,
    var elements: List<ItemElement>,
    var display: ItemDisplay,
    var groups: List<ItemOutLiner>
)

@Serializable
data class ItemElement(
    var from: DoubleArray,
    var to: DoubleArray,
    var rotation: ItemRotation? = null,
    var faces: Map<Direction, ItemElementFace>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ItemElement

        if (!from.contentEquals(other.from)) return false
        if (!to.contentEquals(other.to)) return false
        if (rotation != other.rotation) return false
        if (faces != other.faces) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from.contentHashCode()
        result = 31 * result + to.contentHashCode()
        result = 31 * result + (rotation?.hashCode() ?: 0)
        result = 31 * result + faces.hashCode()
        return result
    }
}

@Serializable
data class ItemRotation(
    var angle: Double,
    var axis: Axis,
    var origin: DoubleArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ItemRotation

        if (angle != other.angle) return false
        if (axis != other.axis) return false
        if (!origin.contentEquals(other.origin)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = angle.hashCode()
        result = 31 * result + axis.hashCode()
        result = 31 * result + origin.contentHashCode()
        return result
    }
}

@Serializable
data class ItemElementFace(
    var uv: DoubleArray,
    var rotation: Double? = null,
    var texture: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ItemElementFace

        if (!uv.contentEquals(other.uv)) return false
        if (rotation != other.rotation) return false
        if (texture != other.texture) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uv.contentHashCode()
        result = 31 * result + (rotation?.hashCode() ?: 0)
        result = 31 * result + texture.hashCode()
        return result
    }
}

@Serializable
data class ItemDisplay(
    var fixed: FixedItemData
)

@Serializable
data class FixedItemData(
    var rotation: DoubleArray,
    var translation: DoubleArray,
    var scale: DoubleArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as FixedItemData

        if (!rotation.contentEquals(other.rotation)) return false
        if (!translation.contentEquals(other.translation)) return false
        if (!scale.contentEquals(other.scale)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rotation.contentHashCode()
        result = 31 * result + translation.contentHashCode()
        result = 31 * result + scale.contentHashCode()
        return result
    }
}

@Serializable
data class ItemGroup(
    var name: String,
    var origin: DoubleArray,
    var children: IntArray
) : ItemOutLiner {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ItemGroup

        if (name != other.name) return false
        if (!origin.contentEquals(other.origin)) return false
        if (!children.contentEquals(other.children)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + origin.contentHashCode()
        result = 31 * result + children.contentHashCode()
        return result
    }
}

@Serializable(with = ItemCubeSerializer::class)
data class ItemCube(val id: Int) : ItemOutLiner
