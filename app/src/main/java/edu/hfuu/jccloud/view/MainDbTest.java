package edu.hfuu.jccloud.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.FormInfo;
import edu.hfuu.jccloud.model.samples.SampleSZ06;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lgb on 21-10-2016.
 */
public class MainDbTest extends Fragment {
    @Bind(R.id.textViewBarcode)
    TextView TVbarcodes;

    @Bind(R.id.textViewSAMPLES01)
    TextView TVsamples;

    @Bind(R.id.queryBarcode)
    Button btQeryBarcodes;

    @Bind(R.id.querySamples)
    Button btQerySamples;

    @Bind(R.id.queryFormInfo)
    Button btQueryFormInfo;

    @Bind(R.id.querySample06)
    Button btQuerySample06;

    private Realm realm;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_test,container,false);
        ButterKnife.bind(this, v);
        realm = Realm.getDefaultInstance();

        btQeryBarcodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<BarCode> results = realm.where(BarCode.class).equalTo("used",true)
                        .findAll();
                int size1=results.size();
                String str="BarCode-Use:[";
                str=str +size1+"]\n";
                if(size1>0){
                    for (BarCode item : results) {
                        if(item.isUsed())
                        str=str+item+"\n";
                    }
                }

                RealmResults<BarCode> results2 = realm.where(BarCode.class).equalTo("used",false)
                        .findAll();
                int size=results2.size();
                String str2="BarCode-UnUsed:[";
                if(size>0){
                    str2=str2 +size+"]\n";
                    for (BarCode item : results) {
                        if(!item.isUsed())
                            str2=str2+item+"\n";
                    }
                }
                TVbarcodes.setText(str+"\n"+str2);

            }
        });

        btQerySamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<SampleSZ06> results = realm.where(SampleSZ06.class)
                        .findAll();
                int size=results.size();
                String str="SampleSZ06-size:["+size+"]\n";
                if(size>0){
                    for (SampleSZ06 item : results) {
                        str=str+item.toString()+"\n";
                    }
                }
                TVsamples.setText(str);

            }
        });

        btQueryFormInfo.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                RealmResults<SampleSZ06> results = realm.where(SampleSZ06.class)
//                        .findAll();
                        RealmResults<FormInfo> results = realm.where(FormInfo.class)
                                .findAll();
                        int size=results.size();
                        String str="FormInfo-size:["+size+"]\n";
                        if(size>0){
                            for (FormInfo item : results) {
                                str=str+item.toString()+"\n";
                            }
                        }
                        TVsamples.setText(str);

                    }
                });

        btQuerySample06.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RealmResults<SampleSZ06> results = realm.where(SampleSZ06.class)
                                .findAll();
                        int size=results.size();
                        String str="FormInfo-size:["+size+"]\n";
                        if(size>0){
                            for (SampleSZ06 item : results) {
                                str=str+item.toString()+"\n";
                            }
                        }
                        TVsamples.setText(str);

                    }
                });

        return v;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
        realm.close();

    }
}