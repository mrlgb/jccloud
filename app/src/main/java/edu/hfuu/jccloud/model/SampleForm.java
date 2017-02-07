package edu.hfuu.jccloud.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SampleForm extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private RealmList<BarCode> barCodes;

    public SampleForm() {
    }

    public SampleForm(String name) {
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

    public RealmList<BarCode> getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(RealmList<BarCode> barCodes) {
        this.barCodes = barCodes;
    }
}
