package edu.hfuu.jccloud.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by mrlgb on 2017/2/6.
 */

public class BarCode extends RealmObject{
    @PrimaryKey
    private String id;
    @Required
    private String bCode;
    private boolean used =false;
    private String sid;

    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setId(String sampleId) {
        this.id = sampleId;
    }

    public String getbCode() {
        return bCode;
    }

    public void setbCode(String bCode) {
        this.bCode = bCode;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
