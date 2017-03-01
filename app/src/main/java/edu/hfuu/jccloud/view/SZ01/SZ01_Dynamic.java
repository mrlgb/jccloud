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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01Adapter;
import edu.hfuu.jccloud.util.RealmHelper;
import edu.hfuu.jccloud.util.cacheHelper;
import edu.hfuu.jccloud.view.BaseFragment;
import edu.hfuu.jccloud.view.RecyclerItemClickListener;
import edu.hfuu.jccloud.view.dialog.AddLocationDialog;
import io.realm.Realm;
import io.realm.RealmResults;

import static edu.hfuu.jccloud.R.id.edtAddresssSZ_O1_Static;
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

    @Bind(edtAddresssSZ_O1_Static)
    EditText edtLocation;
    @Bind(R.id.edtBarCode)
    EditText edtBarCode;
    @Bind(R.id.btnSelectNewLocation)
    Button btnSelectLocation;

    @Bind(R.id.inputLayoutTime)
    TextInputLayout inputTime;
    @Bind(R.id.inputTimePicker)
    EditText edtTime;
    @Bind(R.id.btn_SZ_O1_Dynamic_Add)
    Button btnAdd;
    @Bind(R.id.btn_SZ_O1_Dynamic_Delete)
    Button btnDel;
    @Bind(R.id.btn_SZ_O1_Dynamic_Save)
    Button btnSave;
    @Bind(R.id.btn_SZ_O1_Dynamic_Submit)
    Button btnSubmit;

    private int currentPos = 0;
    private cacheHelper mLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz01_dynamic, container, false);

        ButterKnife.bind(this, v);
        initComponents();
        return v;
    }

    @Override
    protected void initComponents() {
        realm = Realm.getInstance(getContext());
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

        mAdapter = new SampleSZ01Adapter(mDataSet, getContext());
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
                        SampleSZ01 sample = new SampleSZ01("Sample" );//name
                        String uuid = UUID.randomUUID().toString();
                        sample.setId(uuid);//id
                        sample.setAddrSamp(edtLocation.getText().toString());
                        sample.setTimeSamp(edtTime.getText().toString());
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
                    } showMessage("请选择新的采样位置！");

                } else {
                    showMessage("请选择新的采样位置！");
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
                    showMessage("Item[" + position + "]删除完成!");
                } else if (mDataSet.size() == 0) {
//                    showMessage("没有删除项！");
                } else
                    showMessage("请选择删除项！");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("地下水采样现场记录表-样本数据已保存！");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("地下水采样现场记录表-样本数据已提交服务器！");
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
                        timePickerDialog.setTitle("选择时间");
                        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定", timePickerDialog);
                        timePickerDialog.show();
                    }


                });
    }

    private void addItem2SampleDb(final SampleSZ01 sample) {
        realm.beginTransaction();
        SampleSZ01 s = realm.createObject(SampleSZ01.class); // 创建新对象
        s.setAddrSamp(sample.getAddrSamp());
        s.setBarCode(sample.getBarCode());
        s.setTimeSamp(sample.getTimeSamp());
        s.setId(sample.getId());
        realm.commitTransaction();
    }

    private void removeItemInSampleDb(final String bcode) {
        SampleSZ01 code = realm.where(SampleSZ01.class)
                .equalTo("barCode", bcode)
                .findFirst();
        if (code != null) {
            realm.beginTransaction();
            code.removeFromRealm();
            realm.commitTransaction();
        }
    }

    private void initDataSetFromDB() {
        RealmResults<SampleSZ01> results = realm.where(SampleSZ01.class)
                .findAll();
//        RealmResults<SampleSZ01> sortedAscending  = results.sort("index");
        showMessage("地下水采样现场记录表A2-数据刷新完成！");
        if (results.size() > 0) {
//            showMessage("可用样本个数:" + results.size());
            for (int i = 0; i < results.size(); i++) {
                SampleSZ01 item = results.get(i);
                int id = mDataSet.size();
                SampleSZ01 sample = new SampleSZ01("Sample" + id);//name
                sample.setId(item.getId());//id
                sample.setBarCode(item.getBarCode());//bcode
                sample.setAddrSamp(item.getAddrSamp());
                sample.setTimeSamp(item.getTimeSamp());
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
            inputTime.setError("请输入正确的时间（）");
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
        edtLocation.setText(sa.getAddrSamp());
        edtTime.setText(sa.getTimeSamp());
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