package edu.hfuu.jccloud.model.person;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class DogSerializer implements JsonSerializer<Dog> {

    @Override
    public JsonElement serialize(Dog src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        return jsonObject;
    }
}
