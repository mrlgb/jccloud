package edu.hfuu.jccloud.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BarCodeSerializer implements JsonSerializer<BarCode> {

//    private String sampleId;// sample id
//    private boolean used =false;
//    private String groupId; // table id
    @Override
    public JsonElement serialize(BarCode src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", src.getCode());
        jsonObject.addProperty("sample", src.getSampleId());
        jsonObject.addProperty("group", src.getGroupId());
        jsonObject.addProperty("used", src.isUsed());
        return jsonObject;
    }
}
