package edu.hfuu.jccloud.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FormInfo extends RealmObject {
    @PrimaryKey
    private String name;

    private String client;
    private String date;
    private String weather;
    private String equipCharacter;
    private String remark;
    private String sampleColletor;
    private String sampleClient;
    //
    private int SampleDescNum;
    private String SampleDesc1;
    private String SampleDesc2;
    private String SampleDesc3;
    private String SampleDesc4;


    public FormInfo() {
    }

    public FormInfo(String name) {
        this.name = name;
    }
    public FormInfo(String name,int type) {
        this.name = name;
        this.SampleDescNum=type;
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

    public int getSampleDescNum() {
        return SampleDescNum;
    }

    public void setSampleDescNum(int sampleDescNum) {
        SampleDescNum = sampleDescNum;
    }

    public String getSampleDesc1() {
        return SampleDesc1;
    }

    public void setSampleDesc1(String sampleDesc1) {
        SampleDesc1 = sampleDesc1;
    }

    public String getSampleDesc2() {
        return SampleDesc2;
    }

    public void setSampleDesc2(String sampleDesc2) {
        SampleDesc2 = sampleDesc2;
    }

    public String getSampleDesc3() {
        return SampleDesc3;
    }

    public void setSampleDesc3(String sampleDesc3) {
        SampleDesc3 = sampleDesc3;
    }

    public String getSampleDesc4() {
        return SampleDesc4;
    }

    public void setSampleDesc4(String sampleDesc4) {
        SampleDesc4 = sampleDesc4;
    }
}
