import com.benasher44.uuid.uuidFrom
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.lepinoid.bbdatastructure.BBAnimator
import net.lepinoid.bbdatastructure.BBModelData
import net.lepinoid.bbdatastructure.Keyframe
import net.lepinoid.bbdatastructure.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class JVMTest {
    val json = Json

    @Test
    fun serializeTest() {
        val modelJson = this::class.java.getResource("/test.bbmodel")!!.readText()
        val modelData = json.decodeFromString<BBModelData>(modelJson)
        val encoded = json.encodeToString(BBModelData.serializer(), modelData)

        assertEquals(modelData, json.decodeFromString(BBModelData.serializer(), encoded))
    }

    @Test
    fun testAnimatorsMap() {
        val positionId = uuidFrom("22f4e700-1b37-4a4e-b289-2a5ad138c2ec")
        val animatorPosition = BBAnimator(
            "test",
            AnimatorType.BONE,
            listOf(Keyframe(Position(listOf(Vector.ZERO)), positionId, 0.0, -1, "linear"))
        )

        val groupId = uuidFrom("b4b1c345-c100-41ca-9b85-19c8b00460eb")
        val normalData = AnimatorKey.GroupId(groupId) to animatorPosition

        val soundId = uuidFrom("26330c1d-1d01-4019-bc7f-ffb6be6b374c")
        val animatorSound = BBAnimator(
            "Effects",
            AnimatorType.EFFECT,
            listOf(Keyframe(Sound(listOf(SoundData("", ""))), soundId, 0.0, -1, "linear"))
        )
        val effectData = AnimatorKey.EffectData to animatorSound

        val map = AnimatorsMap(hashMapOf(normalData, effectData))

        val encodedMap = json.encodeToString(map)
        val expectedMapData =
            """{"b4b1c345-c100-41ca-9b85-19c8b00460eb":{"name":"test","type":"bone","keyframes":[{"channel":"position","data_points":[{"x":0.0,"y":0.0,"z":0.0}],"uuid":"22f4e700-1b37-4a4e-b289-2a5ad138c2ec","time":0.0,"color":-1,"interpolation":"linear"}]},"effects":{"name":"Effects","type":"effect","keyframes":[{"channel":"sound","data_points":[{"effect":"","file":""}],"uuid":"26330c1d-1d01-4019-bc7f-ffb6be6b374c","time":0.0,"color":-1,"interpolation":"linear"}]}}"""

        assertEquals(expectedMapData, encodedMap)

        val decodedMap = json.decodeFromString<AnimatorsMap>(encodedMap)
        assertEquals(map.entries, decodedMap.entries)
    }
}
