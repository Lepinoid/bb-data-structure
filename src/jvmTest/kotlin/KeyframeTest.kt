import com.benasher44.uuid.uuidFrom
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.lepinoid.bbdatastructure.Keyframe
import net.lepinoid.bbdatastructure.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class KeyframeTest {

    val json = Json {
        prettyPrint = true
    }

    @Test
    fun rotationData() {
        val rotationKey = createKeyframe(rotationData)
        val encoded = json.encodeToString(rotationKey)
        assertEquals(rotationJson + underFrameJson, encoded)

        val decoded = json.decodeFromString<Keyframe>(rotationJson + underFrameJson)
        assertEquals(rotationKey, decoded)
    }


    @Test
    fun positionData() {
        val positionKey = createKeyframe(positionData)
        val encoded = json.encodeToString(positionKey)
        assertEquals(positionJson + underFrameJson, encoded)

        val decoded = json.decodeFromString<Keyframe>(positionJson + underFrameJson)
        assertEquals(positionKey, decoded)
    }

    @Test
    fun scaleData() {
        val scaleKey = createKeyframe(scaleData)
        val encoded = json.encodeToString(scaleKey)
        assertEquals(scaleJson + underFrameJson, encoded)

        val decoded = json.decodeFromString<Keyframe>(scaleJson + underFrameJson)
        assertEquals(scaleKey, decoded)
    }

    @Test
    fun particleData() {
        val particleKey = createKeyframe(particleData)
        val encoded = json.encodeToString(particleKey)
        assertEquals(particleJson + underFrameJson, encoded)

        val decoded = json.decodeFromString<Keyframe>(particleJson + underFrameJson)
        assertEquals(particleKey, decoded)
    }

    @Test
    fun soundData() {
        val soundKey = createKeyframe(soundData)
        val encoded = json.encodeToString(soundKey)
        assertEquals(soundJson + underFrameJson, encoded)

        val decoded = json.decodeFromString<Keyframe>(soundJson + underFrameJson)
        assertEquals(soundKey, decoded)
    }

    @Test
    fun instructionData() {
        val instructionKey = createKeyframe(instructionData)
        val encoded = json.encodeToString(instructionKey)
        assertEquals(instructionJson + underFrameJson, encoded)

        val decoded = json.decodeFromString<Keyframe>(instructionJson + underFrameJson)
        assertEquals(instructionKey, decoded)
    }

    companion object {
        private val uuid = uuidFrom("1dd0f0ef-b38e-3cc5-f7b5-c57b9c616c2f")

        fun createKeyframe(data: KeyframeData) = Keyframe(
            data,
            uuid,
            0.0,
            -1,
            "linear"
        )

        val rotationData = Rotation(listOf(Vector.ZERO))
        val rotationJson = """
        {
            "channel": "rotation",
            "data_points": [
                {
                    "x": 0.0,
                    "y": 0.0,
                    "z": 0.0
                }
            ],
        """.trimIndent()

        val positionData = Position(listOf(Vector.ZERO))
        val positionJson = """
        {
            "channel": "position",
            "data_points": [
                {
                    "x": 0.0,
                    "y": 0.0,
                    "z": 0.0
                }
            ],
        """.trimIndent()

        val scaleData = Scale(listOf(Vector.ZERO))
        val scaleJson = """
        {
            "channel": "scale",
            "data_points": [
                {
                    "x": 0.0,
                    "y": 0.0,
                    "z": 0.0
                }
            ],
        """.trimIndent()

        val particleData = Particle(listOf(ParticleData("effect", "locator", "script", "file")))
        val particleJson = """
        {
            "channel": "particle",
            "data_points": [
                {
                    "effect": "effect",
                    "locator": "locator",
                    "script": "script",
                    "file": "file"
                }
            ],
        """.trimIndent()

        val soundData = Sound(listOf(SoundData("effect", "file")))
        val soundJson = """
        {
            "channel": "sound",
            "data_points": [
                {
                    "effect": "effect",
                    "file": "file"
                }
            ],
        """.trimIndent()

        val instructionData = Instructions(listOf(InstructionData("script")))
        val instructionJson = """
        {
            "channel": "timeline",
            "data_points": [
                {
                    "script": "script"
                }
            ],
        """.trimIndent()

        val underFrameJson = """
        
            "uuid": "1dd0f0ef-b38e-3cc5-f7b5-c57b9c616c2f",
            "time": 0.0,
            "color": -1,
            "interpolation": "linear"
        }
        """.trimIndent()
    }
}