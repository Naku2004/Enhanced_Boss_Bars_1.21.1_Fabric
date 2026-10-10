package com.nakuring.enhanced_boss_bars.bars_loader;

import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class IdentifierAdapter implements JsonDeserializer<Identifier>, JsonSerializer<Identifier> {

    @Override
    public Identifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        return Identifier.tryParse(json.getAsString());
    }

    @Override
    public JsonElement serialize(Identifier src, Type typeOfSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src.toString());
    }
}