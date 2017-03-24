package edu.hfuu.jccloud.model.samples;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleSZ07 extends RealmObject {
    @PrimaryKey
    private String barCode;//code
    private String id;//uuid
    private String index;//0,1,2...
    private String name;
    private String location; //采样点位
    private String sampleTime;//采样时间
    private String sampleNumber;//采样份数
    private String PHValue;
    private String desColor;
    private String desSmell;
    private String desCharacter;
    private String remark;
    private boolean selected;

    public SampleSZ07() {
    }

    public SampleSZ07(String name) {
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

    public String getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(String sampleTime) {
        this.sampleTime = sampleTime;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getPHValue() {
        return PHValue;
    }

    public void setPHValue(String PHValue) {
        this.PHValue = PHValue;
    }

    public String getDesColor() {
        return desColor;
    }

    public void setDesColor(String desColor) {
        this.desColor = desColor;
    }

    public String getDesSmell() {
        return desSmell;
    }

    public void setDesSmell(String desSmell) {
        this.desSmell = desSmell;
    }

    public String getDesCharacter() {
        return desCharacter;
    }

    public void setDesCharacter(String desCharacter) {
        this.desCharacter = desCharacter;
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
