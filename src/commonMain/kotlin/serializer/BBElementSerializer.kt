package net.lepinoid.bbdatastructure.serializer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.lepinoid.bbdatastructure.BBElementCube
import net.lepinoid.bbdatastructure.BBElementLocator
import net.lepinoid.bbdatastructure.util.BBElement

object BBElementSerializer : JsonContentPolymorphicSerializer<BBElement>(BBElement::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out BBElement> {
        val type = element.jsonObject["type"] ?: return BBElementCube.serializer()
        if (type.jsonPrimitive.isString) {
            val typeString = type.jsonPrimitive.content
            if (typeString == "locator") {
                return BBElementLocator.serializer()
            }
        }
        error("Cannot serialize elements. [type] shows incompatible data: $type")
    }
}