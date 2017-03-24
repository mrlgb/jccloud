package edu.hfuu.jccloud.view.samples;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.constants.StringConsts;
import edu.hfuu.jccloud.model.FormInfo;
import edu.hfuu.jccloud.view.BaseFragment;
import io.realm.Realm;

import static edu.hfuu.jccloud.constants.StringConsts.InputCorrectTime;
import static edu.hfuu.jccloud.constants.StringConsts.SampleDataUpdated;
import static edu.hfuu.jccloud.constants.StringConsts.SaveDone;

/**
 * Created by lgb on 21-01-2015.
 */

public class SZ05_Static extends BaseFragment {
    private  String title= StringConsts.SR05A;
    Realm realm;
    @Bind(R.id.btnSaveSZ05Static)
    Button btnSave;
    @Bind(R.id.btnSubmitSZ05Static)
    Button btnSubmit;

    @Bind(R.id.iLayoutClientSZ05Static)
    TextInputLayout inputClient;
    @Bind(R.id.iLayoutDateSZ05Static)
    TextInputLayout inputLaDate;
    @Bind(R.id.iLayoutEquipmentSZ05Static)
    TextInputLayout inputLaEquip;
    @Bind(R.id.iLayoutWeatherSZ05Static)
    TextInputLayout inputLaWeather;

    @Bind(R.id.iLayoutSignClientSZ05Static)
    TextInputLayout inputSignClient;
    @Bind(R.id.iLayoutSignColletorSZ05Static)
    TextInputLayout inputLaSignCollector;


    @Bind(R.id.edtClientSZ05Static)
    EditText edtClient;
    @Bind(R.id.edtEquipmentSZ05Static)
    EditText edtEquip;
    @Bind(R.id.edtWeatherSZ05Static)
    EditText edtWeather;
    @Bind(R.id.edtDateSZ05Static)
    EditText edtDate;

    @Bind(R.id.edtSignColletorSZ05Static)
    EditText edtSigCollect;
    @Bind(R.id.edtSignClientSZ05Static)
    EditText edtSigClient;


    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz05_static, container, false);
        ButterKnife.bind(this, v);
        realm=Realm.getDefaultInstance();
        initComponents();
        return v;
    }

    @Override
    protected void initComponents() {
//        realm = Realm.getInstance(getContext());

        edtDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //To show current date in the datepicker
                        final Calendar mcurrentDate = Calendar.getInstance();
                        mYear = mcurrentDate.get(Calendar.YEAR);
                        mMonth = mcurrentDate.get(Calendar.MONTH);
                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                                mcurrentDate.set(Calendar.YEAR, mYear);
                                mcurrentDate.set(Calendar.MONTH, mMonth);
                                mcurrentDate.set(Calendar.DAY_OF_MONTH, mDay);
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(selectedyear, selectedmonth, selectedday);
                                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CHINA);
                                edtDate.setText(sdf.format(newDate.getTime()));
                            }
                        }, mYear, mMonth, mDay);
                        mDatePicker.setTitle("选择日期");
                        mDatePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定", mDatePicker);
                        mDatePicker.show();
                    }
                });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    saveFormInfoInDB();
//                    Toast.makeText(getContext(), "地下水采样现场记录表保存完成!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(title+"提交网络完成！");
            }
        });

        initFromDB();
    }

    private void initFromDB() {
        FormInfo sampleInDB = realm.where(FormInfo.class)
                .equalTo("name", title)
                .findFirst();
        if (sampleInDB != null) {
            refreshUIByObject(sampleInDB);
            showMessage(sampleInDB.getName() + SampleDataUpdated);
            //....
        }
//        else
//            showMessage(title+ NoSampleData);

    }

    private void refreshUIByObject(FormInfo sampleInDB) {
        edtClient.setText(sampleInDB.getClient());
        edtDate.setText(sampleInDB.getDate());
        edtEquip.setText(sampleInDB.getEquipCharacter());
        edtWeather.setText(sampleInDB.getWeather());
        edtSigCollect.setText(sampleInDB.getSampleColletor());
        edtSigClient.setText(sampleInDB.getSampleClient());

    }

    private FormInfo storeUIToObject() {
        FormInfo formInfo = new FormInfo(StringConsts.SR05A);//name
        formInfo.setClient(edtClient.getText().toString());
        formInfo.setDate(edtDate.getText().toString());
        formInfo.setWeather(edtWeather.getText().toString());
        formInfo.setEquipCharacter(edtEquip.getText().toString());
        formInfo.setSampleClient(edtSigClient.getText().toString());
        formInfo.setSampleColletor(edtSigCollect.getText().toString());
        return formInfo;
    }


    private void saveFormInfoInDB() {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(storeUIToObject());
        realm.commitTransaction();
        showMessage(title+ SaveDone);

    }

    private boolean validateData() {
        boolean result = true;
        String date = edtDate.getText().toString();

        if (date == null || date.length() < 3) {
            // We set the error message
            inputLaDate.setError(InputCorrectTime);
            result = false;
        } else
            // We remove error messages
            inputLaDate.setErrorEnabled(false);

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
        ButterKnife.unbind(this);

    }
}