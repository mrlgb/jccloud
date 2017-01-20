package edu.hfuu.jccloud.SampleSZ;

import java.io.Serializable;

public class SampleSZ01 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String index;
    private String id;
    private String desc;
    private String num;
    private String date;
    private String oxgen;
    private String tDS;
    private String transparency;
    private String depth;
    private String tempe;
    private String color;
    private String smell;
    private String character;
    private String remark;
    boolean selected;

    public SampleSZ01(String id, String index, String desc) {
        this.setId(id);
        this.setIndex(index);
        this.setDesc(desc);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOxgen() {
        return oxgen;
    }

    public void setOxgen(String oxgen) {
        this.oxgen = oxgen;
    }

    public String gettDS() {
        return tDS;
    }

    public void settDS(String tDS) {
        this.tDS = tDS;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getTempe() {
        return tempe;
    }

    public void setTempe(String tempe) {
        this.tempe = tempe;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
