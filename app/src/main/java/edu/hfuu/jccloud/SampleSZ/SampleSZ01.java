package edu.hfuu.jccloud.SampleSZ;

public class SampleSZ01 {
    String id;
    String index;
    String dsg;
    boolean selected;
    public SampleSZ01(String id, String index, String dsg) {
        this.id = id;
        this.index = index;
        this.dsg = dsg;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public String getDsg() {
        return dsg;
    }
    public void setDsg(String dsg) {
        this.dsg = dsg;
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
}
