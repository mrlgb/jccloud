package edu.hfuu.jccloud.model;

import java.util.TreeMap;

public class Project {

    private String id;
    private String name;
    private String index;
    private boolean isPopup;
    private TreeMap<String, MyViewPage> myPages;

    public Project(String name) {
        this.name = name;
        myPages= new TreeMap<>();
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isPopup() {
        return isPopup;
    }

    public void setPopup(boolean popup) {
        isPopup = popup;
    }

    public TreeMap<String, MyViewPage> getMyPages() {
        return myPages;
    }

    public void setMyPages(TreeMap<String, MyViewPage> myPages) {
        this.myPages = myPages;
    }
}
