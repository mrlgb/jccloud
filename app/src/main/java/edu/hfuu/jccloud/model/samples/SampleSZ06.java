package edu.hfuu.jccloud.model.samples;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleSZ06 extends RealmObject {
    @PrimaryKey
    private String barCode;//code
    private String id;//uuid
    private String index;//0,1,2...
    private String name;
    private String location;
    private String depthSamp;
    private String depthWell;
    private String tWater;
    private String desColor;
    private String desSmell;
    private String desCharacter;
    private String PHValue;
    private String sampleTime;
    private String des;
    private String remark;
    private boolean selected;

    public SampleSZ06() {
    }

    public SampleSZ06(String name) {
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

    public void setLocation(String addrSamp) {
        this.location = addrSamp;
    }

    public String getDepthSamp() {
        return depthSamp;
    }

    public void setDepthSamp(String depthSamp) {
        this.depthSamp = depthSamp;
    }

    public String getDepthWell() {
        return depthWell;
    }

    public void setDepthWell(String depthWell) {
        this.depthWell = depthWell;
    }

    public String gettWater() {
        return tWater;
    }

    public void settWater(String tWater) {
        this.tWater = tWater;
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

    public String getPHValue() {
        return PHValue;
    }

    public void setPHValue(String PHValue) {
        this.PHValue = PHValue;
    }

    public String getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(String sampleTime) {
        this.sampleTime = sampleTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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
