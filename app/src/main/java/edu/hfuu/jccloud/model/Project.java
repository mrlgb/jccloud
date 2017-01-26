package edu.hfuu.jccloud.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String desc;
    private String client;
    private String date;
    private ArrayList<Sample> samples;


    public Project(String id, String title, String desc) {
        this.setId(id);
        this.setTitle(title);
        this.setDesc(desc);
        samples = new ArrayList<>(2);
        Sample mysample1 = new Sample(1, "地下水样本A1");
        samples.add(0, mysample1);
        Sample mysample2 = new Sample(2, "地下水样本A2");
        samples.add(1, mysample2);
    }

    @Override
    public String toString() {
        return "Project [id=" + getId()
                + "，title=" + getTitle()
                + "，desc=" + getDesc()
                + ", client=" + getClient()
                + ", date=" + getDate()
                + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public ArrayList<Sample> getSamples() {
        return samples;
    }

    public void setSamples(ArrayList<Sample> samples) {
        this.samples = samples;
    }
}
