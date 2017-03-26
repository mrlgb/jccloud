package edu.hfuu.jccloud.view.samples;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.constants.StringConsts;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.samples.SampleSZ06;
import edu.hfuu.jccloud.model.samples.SampleSZ06Adapter;
import edu.hfuu.jccloud.util.RealmHelper;
import edu.hfuu.jccloud.util.cacheHelper;
import edu.hfuu.jccloud.view.BaseFragment;
import edu.hfuu.jccloud.view.dialog.AddLocationDialog;
import edu.hfuu.jccloud.view.recycleItem.RecyclerItemClickListener;
import io.realm.Realm;
import io.realm.RealmResults;
import mehdi.sakout.fancybuttons.FancyButton;

import static edu.hfuu.jccloud.constants.StringConsts.ChooseItem2Delete;
import static edu.hfuu.jccloud.constants.StringConsts.ChooseNewLocation;
import static edu.hfuu.jccloud.constants.StringConsts.ChooseTime;
import static edu.hfuu.jccloud.constants.StringConsts.DeleteDone;
import static edu.hfuu.jccloud.constants.StringConsts.InputCorrectTime;
import static edu.hfuu.jccloud.constants.StringConsts.SampleDataUpdated;

/**
 * Created by lgb on 21-11-2016.
 */
public class SZ06_Dynamic extends BaseFragment {
    private  String title= StringConsts.SR06B;
    private ArrayList<SampleSZ06> mDataSet;
    private SampleSZ06Adapter mAdapter;
    Realm realm;

    @Bind(R.id.myRecyclerViewSZ06)
    RecyclerView mRecyclerView;

    @Bind(R.id.edtAddresssSZ06)
    EditText edtLocation;
    @Bind(R.id.edtBarCodeSZ06)
    EditText edtBarCode;
    @Bind(R.id.btnSelectNewLocationSZ06)
    FancyButton btnSelectLocation;

    @Bind(R.id.inputLayoutTimeSZ06)
    TextInputLayout inputTime;
    @Bind(R.id.inputTimePickerSZ06)
    EditText edtTime;
    @Bind(R.id.btnDynamicAddSZ06)
    FancyButton btnAdd;
    @Bind(R.id.btnDynamicDeleteSZ06)
    FancyButton btnDel;
    @Bind(R.id.btnDynamicSaveSZ06)
    FancyButton btnSave;
    @Bind(R.id.btnDynamicSubmitSZ06)
    FancyButton btnSubmit;

    private int currentPos = 0;
    private cacheHelper mLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz06_dynamic, container, false);
        ButterKnife.bind(this, v);
        realm  = Realm.getDefaultInstance();
        initComponents();
        return v;
    }

    @Override
    protected void initComponents() {

        mDataSet = new ArrayList<>();
        mLocation = new cacheHelper<>("", "");

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
//                            Toast.makeText(getContext(), "click RecyclerView"+position , Toast.LENGTH_SHORT).show();
                            mAdapter.setSelected(position);
                            updateDetails(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );

        //init dataSet from db
        initDataSetFromDB();

        mAdapter = new SampleSZ06Adapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);

        btnSelectLocation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AddLocationDialog dialog = new AddLocationDialog();
                        dialog.show(getFragmentManager(), dialog.getClass().getName());
                        dialog.setListener(new AddLocationDialog.OnAddLocationListener() {
                            @Override
                            public void onAddLocationClickListener(String location) {
                                edtLocation.setText(location);
                                mLocation.cacahe(location);
                                String bcode = new RealmHelper(getContext()).getFirstUnusedBarcod();
                                edtBarCode.setText(bcode);
                                dialog.dismiss();

                            }
                        });

                    }
                }
        );

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    String bcode = edtBarCode.getText().toString();
                    if (!codeInList(bcode) && !bcode.isEmpty()) {
                        //actual add
                        int index=mDataSet.size();
                        //create new sample item
                        SampleSZ06 sample = new SampleSZ06("Sample" );//name
                        String uuid = UUID.randomUUID().toString();
                        sample.setId(uuid);//id
                        sample.setLocation(edtLocation.getText().toString());
                        sample.setSampleTime(edtTime.getText().toString());
                        sample.setBarCode(bcode);//bcode

                        //set this bcode used and write to Sample in db!
                        addItem2SampleDb(sample);

                        //set this bcode used and write to BarCode in db!
                        registerBarcode(bcode, true, uuid);//set new Barcode used in DB

                        //add to list view
                        mDataSet.add(sample);
                        mAdapter.notifyItemInserted(index);
                        mRecyclerView.scrollToPosition(index);

                        //empty code
                        emptyDeatails();
                    } showMessage(ChooseNewLocation);

                } else {
                    showMessage(ChooseNewLocation);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = mDataSet.size() - 1;
                int position = currentPos;
//                showMessage("准备删除"+position);
                if (position >= 0 && mDataSet.size() != 0 && !edtBarCode.getText().toString().isEmpty()) {
                    String bcode = edtBarCode.getText().toString();
                    mAdapter.setSelected(position - 1);
                    mDataSet.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mRecyclerView.scrollToPosition(position - 1);
                    showMessage("Del item-"+position);

                    //revove item from Sample in DB
                    removeItemInSampleDb(bcode);
                    //Unrigister bacode
                    registerBarcode(edtBarCode.getText().toString(), false, "0000");//db set Unused!!
                    //empty code
                    emptyDeatails();
                    showMessage("Item[" + position + "]"+ DeleteDone);
                } else if (mDataSet.size() == 0) {
//                    showMessage("没有删除项！");
                } else
                    showMessage(ChooseItem2Delete);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(title+StringConsts.SampleDataSaved);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(title+StringConsts.SampleDataSubmitted);
            }
        });

        edtTime.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //To show current date in the datepicker
                        final Calendar c = Calendar.getInstance();
                        final int mHour = c.get(Calendar.HOUR_OF_DAY);
                        final int mMinute = c.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                edtTime.setText(
                                        new StringBuilder()
                                                .append(pad(hourOfDay)).append(":")
                                                .append(pad(minute)));
                            }
                        }, 0, 0, true);
                        timePickerDialog.setTitle(ChooseTime);
                        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定", timePickerDialog);
                        timePickerDialog.show();
                    }


                });
    }

    private void addItem2SampleDb(final SampleSZ06 sample) {
        realm.beginTransaction();
        SampleSZ06 s = realm.createObject(SampleSZ06.class,sample.getBarCode()); // 创建新对象
        s.setLocation(sample.getLocation());
//        s.setBarCode(sample.getBarCode());
        s.setSampleTime(sample.getSampleTime());
        s.setId(sample.getId());
        realm.commitTransaction();
    }

    private void removeItemInSampleDb(final String bcode) {
        SampleSZ06 code = realm.where(SampleSZ06.class)
                .equalTo("barCode", bcode)
                .findFirst();
        if (code != null) {
            realm.beginTransaction();
            code.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    private void initDataSetFromDB() {
        RealmResults<SampleSZ06> results = realm.where(SampleSZ06.class)
                .findAll();
//        RealmResults<SampleSZ06> sortedAscending  = results.sort("index");
        showMessage(title+ SampleDataUpdated);
        if (results.size() > 0) {
//            showMessage("可用样本个数:" + results.size());
            for (int i = 0; i < results.size(); i++) {
                SampleSZ06 item = results.get(i);
                int id = mDataSet.size();
                SampleSZ06 sample = new SampleSZ06("Sample" + id);//name
                sample.setId(item.getId());//id
                sample.setBarCode(item.getBarCode());//bcode
                sample.setLocation(item.getLocation());
                sample.setSampleTime(item.getSampleTime());
                //....
                mDataSet.add(sample);
//                mAdapter.notifyItemInserted(id);
            }
        }
    }

    private boolean validateData() {
        boolean result1 = true;
        boolean result2 = true;

//        String code = edtBarCode.getText().toString();
//        result1 = !code.isEmpty();

        String time = edtTime.getText().toString();

        if (time == null || time.length() < 3) {
            // We set the error message
            inputTime.setError(InputCorrectTime);
            result2 = false;
        } else
            // We remove error messages
            inputTime.setErrorEnabled(false);

        return result1 && result2;
    }

    private void registerBarcode(String barCode, boolean used, String uuid) {
        BarCode code = realm.where(BarCode.class)
                .equalTo("code", barCode)
                .findFirst();
//        showMessage(used ? "Register Barcode:" + code : "Free Barcode: " + code);

        if (code != null) {
            realm.beginTransaction();
            code.setUsed(used);
            code.setSampleId(uuid);
            code.setGroupId(title);
            realm.commitTransaction();
        }
    }

    private boolean codeInList(String code) {
        boolean result = false;
        for (int i = 0; i < mDataSet.size(); i++) {
            if (mDataSet.get(i).getBarCode().contains(code))
                result = true;
        }
        return result;
    }

    public void updateDetails(int position) {
        currentPos = position;

        SampleSZ06 sa = mDataSet.get(position);
        edtBarCode.setText(sa.getBarCode());
        edtLocation.setText(sa.getLocation());
        edtTime.setText(sa.getSampleTime());
    }

    private void emptyDeatails() {
        edtBarCode.setText("");
        edtLocation.setText("");
        edtTime.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        realm.close();
    }

}