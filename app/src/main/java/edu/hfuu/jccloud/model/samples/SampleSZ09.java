package edu.hfuu.jccloud.model.samples;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleSZ09 extends RealmObject {
    @PrimaryKey
    private String barCode;//code
    private String id;//uuid
    private String index;//0,1,2...
    private String name;
    private String location; //采样点位
    private String testitems;//测试项目
    private String sampleStartTime;//采样开始时间
    private String sampleEndTime;//采集结束时间
    private String capacity;//流量
    private String volume;//体积volume
    private String gasVolume;//标态体积
    private String wind;//采样设备情况
    private String windSpeed;
    private String temperature;
    private String atmosphere;
    private String sampleWeather;
    private String remark;
    private boolean selected;

    public SampleSZ09() {
    }

    public SampleSZ09(String name) {
        this.setName(name);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTestitems() {
        return testitems;
    }

    public void setTestitems(String testitems) {
        this.testitems = testitems;
    }

    public String getSampleStartTime() {
        return sampleStartTime;
    }

    public void setSampleStartTime(String sampleStartTime) {
        this.sampleStartTime = sampleStartTime;
    }

    public String getSampleEndTime() {
        return sampleEndTime;
    }

    public void setSampleEndTime(String sampleEndTime) {
        this.sampleEndTime = sampleEndTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getGasVolume() {
        return gasVolume;
    }

    public void setGasVolume(String gasVolume) {
        this.gasVolume = gasVolume;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
    }

    public String getSampleWeather() {
        return sampleWeather;
    }

    public void setSampleWeather(String sampleWeather) {
        this.sampleWeather = sampleWeather;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
