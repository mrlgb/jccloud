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
import edu.hfuu.jccloud.model.samples.SampleSZ08;
import edu.hfuu.jccloud.model.samples.SampleSZ08Adapter;
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
public class SZ08_Dynamic extends BaseFragment {
    private  String title= StringConsts.SR08B;
    private ArrayList<SampleSZ08> mDataSet;
    private SampleSZ08Adapter mAdapter;
    Realm realm;

    @Bind(R.id.myRecyclerViewSZ08)
    RecyclerView mRecyclerView;

    @Bind(R.id.edtAddresssSZ08)
    EditText edtLocation;
    @Bind(R.id.edtBarCodeSZ08)
    EditText edtBarCode;
    @Bind(R.id.btnSelectNewLocationSZ08)
    FancyButton btnSelectLocation;

    @Bind(R.id.inputLayoutStartTimeSZ08)
    TextInputLayout inputStartTime;
    @Bind(R.id.inputLayoutEndTimeSZ08)
    TextInputLayout inputEndTime;
    @Bind(R.id.inputTimePickerSZ08)
    EditText edtStartTime;
    @Bind(R.id.inputTimePickerEndSZ08)
    EditText edtEndTime;
    @Bind(R.id.btnDynamicAddSZ08)
    FancyButton btnAdd;
    @Bind(R.id.btnDynamicDeleteSZ08)
    FancyButton btnDel;
    @Bind(R.id.btnDynamicSaveSZ08)
    FancyButton btnSave;
    @Bind(R.id.btnDynamicSubmitSZ08)
    FancyButton btnSubmit;

    private int currentPos = 0;
    private cacheHelper mLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz08_dynamic, container, false);
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

        mAdapter = new SampleSZ08Adapter(mDataSet, getContext());
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
                        SampleSZ08 sample = new SampleSZ08("Sample" );//name
                        String uuid = UUID.randomUUID().toString();
                        sample.setId(uuid);//id
                        sample.setLocation(edtLocation.getText().toString());
                        sample.setSampleStartTime(edtStartTime.getText().toString());
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

        edtStartTime.setOnClickListener(
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
                                edtStartTime.setText(
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

        edtEndTime.setOnClickListener(
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
                                edtEndTime.setText(
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

    private void addItem2SampleDb(final SampleSZ08 sample) {
        realm.beginTransaction();
        SampleSZ08 s = realm.createObject(SampleSZ08.class,sample.getBarCode()); // 创建新对象
        s.setLocation(sample.getLocation());
//        s.setBarCode(sample.getBarCode());
        s.setSampleStartTime(sample.getSampleStartTime());
        s.setSampleEndTime(sample.getSampleEndTime());
        s.setId(sample.getId());
        realm.commitTransaction();
    }

    private void removeItemInSampleDb(final String bcode) {
        SampleSZ08 code = realm.where(SampleSZ08.class)
                .equalTo("barCode", bcode)
                .findFirst();
        if (code != null) {
            realm.beginTransaction();
            code.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    private void initDataSetFromDB() {
        RealmResults<SampleSZ08> results = realm.where(SampleSZ08.class)
                .findAll();
//        RealmResults<SampleSZ08> sortedAscending  = results.sort("index");
        showMessage(title+ SampleDataUpdated);
        if (results.size() > 0) {
//            showMessage("可用样本个数:" + results.size());
            for (int i = 0; i < results.size(); i++) {
                SampleSZ08 item = results.get(i);
                int id = mDataSet.size();
                SampleSZ08 sample = new SampleSZ08("Sample" + id);//name
                sample.setId(item.getId());//id
                sample.setBarCode(item.getBarCode());//bcode
                sample.setLocation(item.getLocation());
                sample.setSampleStartTime(item.getSampleStartTime());
                sample.setSampleEndTime(item.getSampleEndTime());
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

        String time1 = edtStartTime.getText().toString();

        if (time1 == null || time1.length() < 3) {
            // We set the error message
            inputStartTime.setError(InputCorrectTime);
            result2 = false;
        } else
            // We remove error messages
            inputStartTime.setErrorEnabled(false);

        String time2 = edtEndTime.getText().toString();

        if (time2 == null || time2.length() < 3) {
            // We set the error message
            inputEndTime.setError(InputCorrectTime);
            result2 = false;
        } else
            // We remove error messages
            inputEndTime.setErrorEnabled(false);

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

        SampleSZ08 sa = mDataSet.get(position);
        edtBarCode.setText(sa.getBarCode());
        edtLocation.setText(sa.getLocation());
        edtStartTime.setText(sa.getSampleStartTime());
        edtEndTime.setText(sa.getSampleEndTime());
    }

    private void emptyDeatails() {
        edtBarCode.setText("");
        edtLocation.setText("");
        edtStartTime.setText("");
        edtEndTime.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        realm.close();
    }

}