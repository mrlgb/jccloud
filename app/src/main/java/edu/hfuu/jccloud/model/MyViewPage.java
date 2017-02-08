package edu.hfuu.jccloud.model;

/**
 * Created by mrlgb on 2017/2/8.
 */

public class MyViewPage  {
    private String id;
    private String index;
    private String className;
    private String title;

    public MyViewPage( String index, String className, String title) {
        this.index = index;
        this.className = className;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
