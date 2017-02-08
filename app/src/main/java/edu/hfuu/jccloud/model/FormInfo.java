package edu.hfuu.jccloud.model;

import io.realm.RealmObject;

public class FormInfo extends RealmObject {
    private String id;
    private String name;

    private String client;
    private String date;
    private String weather;
    private String equipCharacter;
    private String remark;
    private String sampleColletor;
    private String sampleClient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getEquipCharacter() {
        return equipCharacter;
    }

    public void setEquipCharacter(String equipCharacter) {
        this.equipCharacter = equipCharacter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampleColletor() {
        return sampleColletor;
    }

    public void setSampleColletor(String sampleColletor) {
        this.sampleColletor = sampleColletor;
    }

    public String getSampleClient() {
        return sampleClient;
    }

    public void setSampleClient(String sampleClient) {
        this.sampleClient = sampleClient;
    }
}
