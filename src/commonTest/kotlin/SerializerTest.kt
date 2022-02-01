import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.lepinoid.bbdatastructure.BBElementCube
import net.lepinoid.bbdatastructure.BBElementLocator
import net.lepinoid.bbdatastructure.serializer.BBElementSerializer
import net.lepinoid.bbdatastructure.serializer.vector.ArrayLikeVectorSerializer
import net.lepinoid.bbdatastructure.util.Plane
import net.lepinoid.bbdatastructure.util.UV
import net.lepinoid.bbdatastructure.util.Vector
import kotlin.test.Test
import kotlin.test.assertTrue

class SerializerTest {

    val defaultJson = Json

    @Test
    fun testArrayLikeVectorSerializer() {
        val vec = Vector(0.0, 0.0, 0.0)
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

}