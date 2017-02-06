package edu.hfuu.jccloud.model;

/**
 * Created by mrlgb on 2017/2/6.
 */

public class BinaryCode {
    private String sampleId;
    private String bCode;
    private boolean used =false;

    public BinaryCode(String bCode, boolean used,String sampleId) {
        this.sampleId = sampleId;
        this.used = used;
        this.bCode = bCode;
    }
}
