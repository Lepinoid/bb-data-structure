import com.benasher44.uuid.uuidFrom
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.lepinoid.bbdatastructure.BBAnimator
import net.lepinoid.bbdatastructure.BBElementCube
import net.lepinoid.bbdatastructure.BBElementLocator
import net.lepinoid.bbdatastructure.Keyframe
import net.lepinoid.bbdatastructure.serializer.BBElementSerializer
import net.lepinoid.bbdatastructure.serializer.vector.ArrayLikeVectorSerializer
import net.lepinoid.bbdatastructure.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializerTest {

    val defaultJson = Json

    @Test
    fun testArrayLikeVectorSerializer() {
        val vec = Vector.ZERO
        val encodedVec = defaultJson.encodeToString(ArrayLikeVectorSerializer, vec)
        println("vec: $encodedVec")
        val list = listOf(0.0, 0.0, 0.0)
        val encodedList = defaultJson.encodeToString(list)
        println("list: $encodedList")
        assertTrue { encodedVec == encodedList }

        val decodedVec = defaultJson.decodeFromString(ArrayLikeVectorSerializer, encodedVec)
        println("decode: $decodedVec")
        assertTrue { vec == decodedVec }
    }

    @Test
    fun testElementSerializer() {
        val elementCube =
            """{"name":"cube","rescale":false,"from":[-0.6078267269900357,32.379999999999995,-1.785846053785316],"to":[1.9921732730099644,34.980000000000004,0.814153946214684],"autouv":0,"color":4,"locked":false,"rotation":[62.6951,22.7992,36.8924],"origin":[0.5621732730099643,33.68,-0.6158460537853162],"faces":{"north":{"uv":[2,2,4,4],"texture":0},"east":{"uv":[0,2,2,4],"texture":0},"south":{"uv":[6,2,8,4],"texture":0},"west":{"uv":[4,2,6,4],"texture":0},"up":{"uv":[4,2,2,0],"texture":0},"down":{"uv":[6,0,4,2],"texture":0}},"uuid":"8a86f331-2747-4fef-494e-bbebc577703f"}"""
        val cube = defaultJson.decodeFromString(BBElementSerializer, elementCube)
        println("cube: $cube")
        assertTrue { cube is BBElementCube }
        val elementLocator =
            """{"name":"locator","from":[2,13,0],"locked":false,"uuid":"3ec86c0e-c04e-f9f8-6ac2-567c87309aae","type":"locator"}"""
        val locator = defaultJson.decodeFromString(BBElementSerializer, elementLocator)
        println("locator: $locator")
        assertTrue { locator is BBElementLocator }
    }

    @Test
    fun testPlaneSerializer() {
        val plane = Plane(0.0, 0.0)
        val encodedPlane = defaultJson.encodeToString(plane)
        println("plane: $encodedPlane")

        val list = listOf(0.0, 0.0)
        val encodedList = defaultJson.encodeToString(list)
        println("list: $encodedList")
        assertTrue { encodedPlane == encodedList }

        val decodePlane = defaultJson.decodeFromString<Plane>(encodedPlane)
        println("decode: $decodePlane")
        assertTrue { plane == decodePlane }
    }

    @Test
    fun testUVSerializer() {
        val uv = UV(Plane(0.0, 0.0), Plane(0.0, 0.0))
        val encodedUV = defaultJson.encodeToString(uv)
        println("uv: $encodedUV")

        val list = listOf(0.0, 0.0, 0.0, 0.0)
        val encodedList = defaultJson.encodeToString(list)
        println("list: $encodedList")
        assertTrue { encodedUV == encodedList }

        val decodeUV = defaultJson.decodeFromString<UV>(encodedUV)
        println("decode: $decodeUV")
        assertTrue { uv == decodeUV }
    }

    @Test
    fun testLoopTypeSerializer() {
        val once = LoopType.ONCE
        val loop = LoopType.LOOP
        val hold = LoopType.HOLD

        val encodedOnce = defaultJson.encodeToString(once)
        assertEquals("\"once\"", encodedOnce)
        val encodedLoop = defaultJson.encodeToString(loop)
        assertEquals("\"loop\"", encodedLoop)
        val encodedHold = defaultJson.encodeToString(hold)
        assertEquals("\"hold\"", encodedHold)

        val decodedOnce = defaultJson.decodeFromString<LoopType>(encodedOnce)
        assertEquals(once, decodedOnce)
        val decodedLoop = defaultJson.decodeFromString<LoopType>(encodedLoop)
        assertEquals(loop, decodedLoop)
        val decodedHold = defaultJson.decodeFromString<LoopType>(encodedHold)
        assertEquals(hold, decodedHold)
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

        val encodedMap = defaultJson.encodeToString(map)
        val expectedMapData =
            """{"b4b1c345-c100-41ca-9b85-19c8b00460eb":{"name":"test","type":"bone","keyframes":[{"channel":"position","data_points":[{"x":0.0,"y":0.0,"z":0.0}],"uuid":"22f4e700-1b37-4a4e-b289-2a5ad138c2ec","time":0.0,"color":-1,"interpolation":"linear"}]},"effects":{"name":"Effects","type":"effect","keyframes":[{"channel":"sound","data_points":[{"effect":"","file":""}],"uuid":"26330c1d-1d01-4019-bc7f-ffb6be6b374c","time":0.0,"color":-1,"interpolation":"linear"}]}}"""

        assertEquals(expectedMapData, encodedMap)

        val decodedMap = defaultJson.decodeFromString<AnimatorsMap>(encodedMap)
        assertEquals(map.entries, decodedMap.entries)
    }
}
