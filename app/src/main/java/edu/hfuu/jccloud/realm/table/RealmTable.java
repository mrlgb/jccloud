package edu.hfuu.jccloud.realm.table;

/**
 * Created by roma on 16.10.15.
 */
public interface RealmTable {

    String ID = "id";

    interface SampleForm {
        String BARCODES = "barCodes";
        String NAME = "name";
    }

    interface BarCode{
        String NAME = "name";
    }
}
