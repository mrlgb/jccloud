package edu.hfuu.jccloud.model.person;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PersonSerializer implements JsonSerializer<Person> {

    @Override
    public JsonElement serialize(Person src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("age", src.getAge());
        jsonObject.add("favoriteDog", context.serialize(src.getFavoriteDog()));
        jsonObject.add("dogs", context.serialize(src.getDogs()));
        return jsonObject;
    }
}
