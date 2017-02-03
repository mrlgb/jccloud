package edu.hfuu.jccloud.model;

import java.io.Serializable;

public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String uniDesc;

    private String client;
    private String date;
    private String weather;
    private String equipCharacter;
    private String remark;
    private String sampleColletor;
    private String sampleClient;


    public Sample(int id, String unidesc) {
        this.setId(id);
        this.setUnidesc(unidesc);
    }

    @Override
    public String toString() {
        return "Sample [id=" + id
                + "，uniDesc="+ uniDesc
                +"，client=" + client
                + ", date=" + date
                + ", weather=" + weather
                + ", equipCharacter=" + equipCharacter
                + ", remark=" + remark
                + ", sampleColletor=" + sampleColletor
                + ", sampleClient=" + sampleClient
                + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnidesc() {
        return uniDesc;
    }

    public void setUnidesc(String unidesc) {
        this.uniDesc = unidesc;
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
