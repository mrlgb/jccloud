package edu.hfuu.jccloud.realm.module;

import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.SampleForm;
import io.realm.annotations.RealmModule;

/**
 * Created by roma on 15.10.15.
 */
@RealmModule(classes = {BarCode.class, SampleForm.class})
public class SimpleRealmModule {

}
