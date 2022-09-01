package net.lepinoid.bbdatastructure.util

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.BBAnimator
import net.lepinoid.bbdatastructure.BBGroup
import net.lepinoid.bbdatastructure.serializer.AnimatorKeySerializer
import net.lepinoid.uuidserializer.UuidSerializer
import kotlin.jvm.JvmInline

@Serializable
class AnimatorsMap(private val map: Map<AnimatorKey, BBAnimator>) : Map<AnimatorKey, BBAnimator> by map

@Serializable(AnimatorKeySerializer::class)
sealed interface AnimatorKey {
    /**
     * [uuid] == [BBGroup.uuid]
     */
    @JvmInline
    value class GroupId(@Serializable(UuidSerializer::class) val uuid: Uuid) : AnimatorKey

    object EffectData : AnimatorKey
}
