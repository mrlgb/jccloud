package edu.hfuu.jccloud.model.samples;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleSZ08 extends RealmObject {
    @PrimaryKey
    private String barCode;//code
    private String id;//uuid
    private String index;//0,1,2...
    private String name;
    private String location; //采样点位
    private String sampleStartTime;//采样开始时间
    private String sampleEndTime;//采集结束时间
    private String precipitation;//降水量
    private String precipitationType;//类型
    private String sampleNow;//样品 现状
    private String sapleEquipmentDes;//采样设备情况
    private String remark;
    private boolean selected;

    public SampleSZ08() {
    }

    public SampleSZ08(String name) {
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

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }

    public String getSampleNow() {
        return sampleNow;
    }

    public void setSampleNow(String sampleNow) {
        this.sampleNow = sampleNow;
    }

    public String getSapleEquipmentDes() {
        return sapleEquipmentDes;
    }

    public void setSapleEquipmentDes(String sapleEquipmentDes) {
        this.sapleEquipmentDes = sapleEquipmentDes;
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
