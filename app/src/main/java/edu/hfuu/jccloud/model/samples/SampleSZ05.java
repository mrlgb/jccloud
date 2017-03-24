package edu.hfuu.jccloud.model.samples;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleSZ05 extends RealmObject {
    @PrimaryKey
    private String barCode;//code
    private String id;//uuid
    private String index;//0,1,2...
    private String name;
    private String location;//断面或采样点
    private String depth;//水深
    private String velocity;//流速
    private String capacity;//流量 m3/s
    private String waterColor;//水颜色
    private String waterSmell;//水气味
    private String floatableThing;//水面油膜 及漂浮物
    private String temperature;//水温
    private String transparency;//透明度cm
    private String PHValue;//PH
    private String DOValue;//DO
    private String conductivity;//电导率S/cm
    private String sampleTime;//采样时间

    private String remark;//备注
    private boolean selected;

    public SampleSZ05() {
    }

    public SampleSZ05(String name) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getWaterColor() {
        return waterColor;
    }

    public void setWaterColor(String waterColor) {
        this.waterColor = waterColor;
    }

    public String getWaterSmell() {
        return waterSmell;
    }

    public void setWaterSmell(String waterSmell) {
        this.waterSmell = waterSmell;
    }

    public String getFloatableThing() {
        return floatableThing;
    }

    public void setFloatableThing(String waterFloatableThing) {
        this.floatableThing = waterFloatableThing;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getPHValue() {
        return PHValue;
    }

    public void setPHValue(String PHValue) {
        this.PHValue = PHValue;
    }

    public String getDOValue() {
        return DOValue;
    }

    public void setDOValue(String DOValue) {
        this.DOValue = DOValue;
    }

    public String getConductivity() {
        return conductivity;
    }

    public void setConductivity(String conductivity) {
        this.conductivity = conductivity;
    }

    public String getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(String sampleTime) {
        this.sampleTime = sampleTime;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
