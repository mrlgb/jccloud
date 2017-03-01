package edu.hfuu.jccloud.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.BarCodeSerializer;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by lgb on 21-10-2016.
 */
public class MainDbSerilizeTest extends Fragment {
    @Bind(R.id.textViewDb)
    TextView tvResult;

    @Bind(R.id.textViewCache)
    TextView tvCache;

    @Bind(R.id.serialize1)
    Button btSerialize1;
    @Bind(R.id.serialize2)
    Button btSerialize2;


    private Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.realm_test, container, false);
        ButterKnife.bind(this, v);
        realm = Realm.getDefaultInstance();

        btSerialize1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .registerTypeAdapter(BarCode.class, new BarCodeSerializer())
                        .setPrettyPrinting()
                        .create();

                // Serialize a Realm object to a JSON string
                BarCode realmObj=realm.where(BarCode.class).findFirst();
                if(realmObj != null) {
                    realmObj = realm.copyFromRealm(realmObj); //detach from Realm, copy values to fields
                    Log.e("Barcode_GSON:", gson.toJson(realmObj));
                }

            }
        });

        btSerialize2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Gson gson = new GsonBuilder()
//                        .setExclusionStrategies(new ExclusionStrategy() {
//                            @Override
//                            public boolean shouldSkipField(FieldAttributes f) {
//                                return f.getDeclaringClass().equals(RealmObject.class);
//                            }
//
//                            @Override
//                            public boolean shouldSkipClass(Class<?> clazz) {
//                                return false;
//                            }
//                        })
//                        .setPrettyPrinting()
//                        .create();
//                BarCode code =new BarCode();
//                code.setCode("xxxxxx-000000000000000-xxx");
//                code.setGroupId("group0");
//                code.setUsed(true);
//                code.setSampleId("000001-92789-hero");
//                String json = gson.toJson(code);
//                tvCache.setText("");
//                tvCache.setText("Cache:"+"\n"+code.toString());
//                tvResult.setText("");
//                tvResult.setText("GSON："+"\n");
//                tvResult.append(json);
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        realm.close();
        super.onDestroyView();

    }
}