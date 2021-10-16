package net.lepinoid.bbdatastructure.util

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.BBElementSerializer

@Serializable(with = BBElementSerializer::class)
interface BBElement {
    var name: String
    var from: Vector
    var isLocked: Boolean
    var uuid: Uuid
    var type: String?
}