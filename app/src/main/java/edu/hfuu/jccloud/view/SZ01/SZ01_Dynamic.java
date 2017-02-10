package edu.hfuu.jccloud.view.SZ01;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import edu.hfuu.jccloud.view.RecyclerItemClickListener;
import edu.hfuu.jccloud.view.dialog.AddBarCodeDialog;
import io.realm.Realm;

import static edu.hfuu.jccloud.R.id.my_recycler_view;

/**
 * Created by lgb on 21-11-2016.
 */
public class SZ01_Dynamic extends Fragment {
    private ArrayList<SampleSZ01> mDataSet;
    private SampleSZ01Adapter mAdapter;

    @Bind(my_recycler_view)
    RecyclerView mRecyclerView;


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

        //
        mDataSet = new ArrayList<>();
        mBarcode=new cacheHelper<> ("","");

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
                                dialog.dismiss();
                                if (!barCode.isEmpty()){
                                    mBarcode.cacahe(barCode);
                                    setBarcodeUsed(barCode,true);//set new Barcode used in DB
//                                    Toast.makeText(getContext(), "[ old]+" + mBarcode.getOldItem() + " [new]"+mBarcode.getNowItem()+"/"+mBarcode.getCacheTimes(), Toast.LENGTH_SHORT).show();
                                    if(mBarcode.getNowItem()!=mBarcode.getOldItem()&& mBarcode.getCacheTimes()>1){
//                                        Toast.makeText(getContext(), "[ " + mBarcode.getOldItem() + " ]set unused back", Toast.LENGTH_SHORT).show();
                                        setBarcodeUsed(mBarcode.getOldItem().toString(),false);//set old barcode unused back in DB!!
                                    }
                                }

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
                    if (!codeInList(edtBarCode.getText().toString())) {
                        //actual add
                        int id = mDataSet.size();
                        SampleSZ01 sample = new SampleSZ01("Sample" + id);//name
                        sample.setId(UUID.randomUUID().toString());//id
                        sample.setIndex("" + id);//index
                        sample.setBarCode(edtBarCode.getText().toString());//bcode

                        mDataSet.add(id, sample);
                        mAdapter.notifyItemInserted(id);
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                        Toast.makeText(getContext(), "Item[ " + id + " ]添加完成！", Toast.LENGTH_SHORT).show();
                        //empty code
                        edtBarCode.setText("");
                    }

                } else {
                    Toast.makeText(getContext(), "请选择新的条码！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = mDataSet.size() - 1;
                int position = currentPos;
                if (position >= 0 && mDataSet.size() != 0 && !edtBarCode.getText().toString().isEmpty()) {
                    mAdapter.setSelected(position - 1);
                    mDataSet.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mRecyclerView.scrollToPosition(position - 1);
                    //
                    setBarcodeUsed(edtBarCode.getText().toString(),false);//db set Unused!!
                    //empty code
                    edtBarCode.setText("");
                    Toast.makeText(getContext(), "Item[" + position + "]删除完成!", Toast.LENGTH_SHORT).show();
                } else
                if(mDataSet.size()==0){
                    Toast.makeText(getContext(), "没有删除项！", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "请选择删除项！", Toast.LENGTH_SHORT).show();



            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    Toast.makeText(getContext(), "All sample have been saved!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
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

    private void setBarcodeUsed(String barCode ,boolean used) {
        Realm realm = Realm.getInstance(getContext());
        BarCode code = realm.where(BarCode.class)
                .equalTo("code", barCode)
                .findFirst();
//        Toast.makeText(getContext(), "" + code, Toast.LENGTH_SHORT).show();
        realm.beginTransaction();
        code.setUsed(used);
        realm.commitTransaction();

    }

    private boolean codeInList(String code) {
        boolean result = false;
        for (int i = 0; i < mDataSet.size(); i++) {
            if (mDataSet.get(i).getBarCode().contains(code))
                result = true;
        }
        if (result == true)
            Toast.makeText(getContext(), "请选择新的条码！", Toast.LENGTH_SHORT).show();
        return result;
    }

    public void updateDetails(int position) {
        currentPos = position;
        SampleSZ01 sa = mDataSet.get(position);
        edtBarCode.setText(sa.getBarCode());

    }

    /**
     * Add padding to numbers less than ten
     */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}