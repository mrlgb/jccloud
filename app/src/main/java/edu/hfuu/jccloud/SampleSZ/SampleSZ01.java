package edu.hfuu.jccloud.sampleSZ;

import java.io.Serializable;

public class SampleSZ01 implements Serializable {
    private static final long serialVersionUID = 1L;
//    private int uuid;
    private String index;
    private String addrSamp;
    private String depthSamp;
    private String depthWell;
    private String tWater;
    private String desColor;
    private String desSmell;
    private String desCharacter;
    private String PHValue;
    private String timeSamp;
    private String des;

    private String remark;
    boolean selected;

    public SampleSZ01(String index, String desc) {
        this.setIndex(index);
        this.setDes(desc);
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAddrSamp() {
        return addrSamp;
    }

    public void setAddrSamp(String addrSamp) {
        this.addrSamp = addrSamp;
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

    public String getTimeSamp() {
        return timeSamp;
    }

    public void setTimeSamp(String timeSamp) {
        this.timeSamp = timeSamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
