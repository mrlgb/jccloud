package edu.hfuu.jccloud.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleInfo extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private BarCode barCode;

    public SampleInfo() {
    }

    public SampleInfo(String name) {
        this.name = name;
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

    public BarCode getBarCode() {
        return barCode;
    }

    public void setBarCode(BarCode barCode) {
        this.barCode = barCode;
    }
}
