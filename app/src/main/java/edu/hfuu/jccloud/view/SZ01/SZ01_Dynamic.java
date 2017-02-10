package edu.hfuu.jccloud.view.SZ01;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01Adapter;
import edu.hfuu.jccloud.util.cacheHelper;
import edu.hfuu.jccloud.view.BaseFragment;
import edu.hfuu.jccloud.view.RecyclerItemClickListener;
import edu.hfuu.jccloud.view.dialog.AddBarCodeDialog;
import io.realm.Realm;
import io.realm.RealmResults;

import static edu.hfuu.jccloud.R.id.my_recycler_view;

/**
 * Created by lgb on 21-11-2016.
 */
public class SZ01_Dynamic extends BaseFragment {
    private ArrayList<SampleSZ01> mDataSet;
    private SampleSZ01Adapter mAdapter;
    Realm realm;

    @Bind(my_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.edtSampleId)
    EditText edtSampleID;
    @Bind(R.id.edtBarCode)
    EditText edtBarCode;
    @Bind(R.id.btnSelectNewBarCode)
    Button btnSelectNewCode;

    @Bind(R.id.inputLayoutTime)
    TextInputLayout inputLayoutTime;
    @Bind(R.id.inputTimePicker)
    EditText edtTime;
    @Bind(R.id.btn_SZ_O1_Dynamic_Add)
    Button btnAdd;
    @Bind(R.id.btn_SZ_O1_Dynamic_Delete)
    Button btnDel;
    @Bind(R.id.btn_SZ_O1_Dynamic_Save)
    Button btnSave;

    private int currentPos = 0;
    private cacheHelper mBarcode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz01_dynamic, container, false);

        ButterKnife.bind(this, v);
        initComponents();
        return v;
    }

    @Override
    protected void initComponents() {
        mDataSet = new ArrayList<>();
        mBarcode = new cacheHelper<>("", "");
        realm = Realm.getInstance(getContext());

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

        mAdapter = new SampleSZ01Adapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);

        btnSelectNewCode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AddBarCodeDialog dialog = new AddBarCodeDialog();
                        dialog.show(getFragmentManager(), dialog.getClass().getName());
                        dialog.setListener(new AddBarCodeDialog.OnAddBarCodeListener() {
                            @Override
                            public void onAddBarCodeClickListener(String barCode) {
                                edtBarCode.setText(barCode);
                                if (!barCode.isEmpty()) {
                                    mBarcode.cacahe(barCode);
//                                    showMessage("[ old：+" + mBarcode.getOldItem() + "] [new" + mBarcode.getNowItem() + "]/" + mBarcode.getCacheTimes());
                                    if (mBarcode.getNowItem() != mBarcode.getOldItem()//最近两次选择不同
                                            && mBarcode.getCacheTimes() > 1//点击选择2次以上
                                            && !codeInList(mBarcode.getOldItem().toString()//不在dataSet
                                    )) {
                                       showMessage("[ " + mBarcode.getOldItem() + " ]free!");
                                        registerBarcode(mBarcode.getOldItem().toString(), false, "0000");//set old barcode unused back in DB!!
                                    }
                                }
                                dialog.dismiss();

                            }
                        });

                    }
                }
        );

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
                        timePickerDialog.setTitle("选择时间");
                        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定", timePickerDialog);
                        timePickerDialog.show();
                    }


                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    String bcode = edtBarCode.getText().toString();
                    if (!codeInList(bcode) && !bcode.isEmpty()) {
                        //actual add
                        int id = mDataSet.size();

                        //create new sample item
                        SampleSZ01 sample = new SampleSZ01("Sample" + id);//name
                        String uuid = UUID.randomUUID().toString();
                        sample.setId(uuid);//id
                        sample.setIndex("" + id);//index
                        sample.setBarCode(bcode);//bcode

                        //set this bcode used and write to Sample in db!
                        addItem2SampleDb(sample);
                        //set this bcode used and write to BarCode in db!
                        registerBarcode(bcode, true, uuid);//set new Barcode used in DB

                        //add to list view
                        mDataSet.add(id, sample);
                        mAdapter.notifyItemInserted(id);
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                        //empty code
                        edtBarCode.setText("");
                    }

                } else {
                    showMessage("请选择新的条码！");
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = mDataSet.size() - 1;
                int position = currentPos;
                showMessage("准备删除"+position);
                if (position >= 0 && mDataSet.size() != 0 && !edtBarCode.getText().toString().isEmpty()) {
                    String bcode=edtBarCode.getText().toString();
                    mAdapter.setSelected(position - 1);
                    mDataSet.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mRecyclerView.scrollToPosition(position - 1);

                    //revove item from Sample in DB
                    removeItemInSampleDb(bcode);
                    //Unrigister bacode
                    registerBarcode(edtBarCode.getText().toString(), false, "0000");//db set Unused!!
                    //empty code
                    edtBarCode.setText("");
                    showMessage("Item[" + position + "]删除完成!");
                } else if (mDataSet.size() == 0) {
                    showMessage("没有删除项！");
                } else
                    showMessage("请选择删除项！");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("所有数据已保存！");
            }
        });
    }


    private void addItem2SampleDb(final SampleSZ01 sample) {
        realm.beginTransaction();
        SampleSZ01 s = realm.createObject(SampleSZ01.class); // 创建新对象
        s.setBarCode(sample.getBarCode());
        s.setId(sample.getId());
        s.setIndex(sample.getIndex());
        s.setName("Sample" + sample.getIndex());
        realm.commitTransaction();
    }

    private void removeItemInSampleDb(final String bcode) {
        SampleSZ01 code = realm.where(SampleSZ01.class)
                .equalTo("barCode", bcode)
                .findFirst();
        realm.beginTransaction();
        code.removeFromRealm();
        realm.commitTransaction();
    }

    private void initDataSetFromDB() {
        RealmResults<SampleSZ01> results = realm.where(SampleSZ01.class)
                .findAll();
//        RealmResults<SampleSZ01> sortedAscending  = results.sort("index");
        showMessage("刷新数据项:" + results.size());
        if (results.size() > 0) {
//            showMessage("可用样本个数:" + results.size());
            for (int i = 0; i < results.size(); i++) {
                SampleSZ01 item = results.get(i);
                int id = mDataSet.size();
                SampleSZ01 sample = new SampleSZ01("Sample" + id);//name
                sample.setId(item.getId());//id
                sample.setIndex(item.getIndex());//index
                sample.setBarCode(item.getBarCode());//bcode
                //....
                mDataSet.add(sample);
//                mAdapter.notifyItemInserted(id);
            }
        }
    }

    private boolean validateData() {
        boolean result1;
        boolean result2 = true;

        String code = edtBarCode.getText().toString();
        result1 = !code.isEmpty();

//        String time = edtTime.getText().toString();
//
//        if (time == null || time.length() < 3) {
//            // We set the error message
//            inputLayoutTime.setError("请输入正确的时间（）");
//            result2 = false;
//        } else
//            // We remove error messages
//            inputLayoutTime.setErrorEnabled(false);

        return result1 && result2;
    }

    private void registerBarcode(String barCode, boolean used, String uuid) {
        BarCode code = realm.where(BarCode.class)
                .equalTo("code", barCode)
                .findFirst();
        Toast.makeText(getContext(), used ? "Register Barcode:" + code : "Free Barcode: " + code, Toast.LENGTH_SHORT).show();

        if (code != null) {
            realm.beginTransaction();
            code.setUsed(used);
            code.setSampleId(uuid);
            code.setGroupId("地下水采样现场记录A2");
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

        SampleSZ01 sa = mDataSet.get(position);
        edtBarCode.setText(sa.getBarCode());
        edtSampleID.setText(sa.getIndex());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        realm.close();
    }

}