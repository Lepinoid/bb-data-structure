import kotlinx.serialization.json.Json
import net.lepinoid.bbdatastructure.BBModelData
import kotlin.test.Test
import kotlin.test.assertEquals

class JVMTest {

    @Test
    fun serializeTest() {
        val json = Json
        val modelJson = this::class.java.getResource("/test.bbmodel")!!.readText()
        val modelData = json.decodeFromString<BBModelData>(BBModelData.serializer(), modelJson)
        println(modelData)
        val encoded = json.encodeToString(BBModelData.serializer(), modelData)

        assertEquals(modelData, json.decodeFromString(BBModelData.serializer(), encoded))
    }
}