package edu.hfuu.jccloud.view.SZ01;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01Adapter;
import edu.hfuu.jccloud.view.RecyclerItemClickListener;
import io.realm.Realm;
import io.realm.RealmResults;

import static edu.hfuu.jccloud.R.id.my_recycler_view;

/**
 * Created by lgb on 21-11-2016.
 */
public class SZ01_Dynamic extends Fragment {
    private ArrayList<SampleSZ01> mDataSet;
    private SampleSZ01Adapter mAdapter;
    private TreeMap<String, BarCode> codeMapNoUsed;
    private TreeMap<String, BarCode> codeMapUsed;
//    private List<BarCode> codesList;
//    private List<String> codesStrList;

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

    Realm realm;

    private int iSampl = 0;
    private int currentPos = 0;


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
        codeMapNoUsed = new TreeMap<>();
        codeMapUsed = new TreeMap<>();
        listUnusedBarcode();
        mDataSet = new ArrayList<>();

        mAdapter = new SampleSZ01Adapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);

//        btnSelectNewCode.setOnClickListener(
//                new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AddBarCodeDialog dialog = new AddBarCodeDialog();
//                dialog.show(getFragmentManager(), dialog.getClass().getName());
//                dialog.setListener(new AddBarCodeDialog.OnAddBarCodeListener() {
//                    @Override
//                    public void onAddBarCodeClickListener(Object barCode) {
//                        edtBarCode.setText(barCode.toString());
//                        dialog.dismiss();
//                    }
//                });
//
//            }
//        }
//        );

//        edtTime.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        //To show current date in the datepicker
//                        final Calendar c = Calendar.getInstance();
//                        final int mHour = c.get(Calendar.HOUR_OF_DAY);
//                        final int mMinute = c.get(Calendar.MINUTE);
//
//                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                edtTime.setText(
//                                        new StringBuilder()
//                                                .append(pad(hourOfDay)).append(":")
//                                                .append(pad(minute)));
//                            }
//                        }, 0, 0, true);
//                        timePickerDialog.setTitle("选择时间");
//                        timePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定", timePickerDialog);
//                        timePickerDialog.show();
//                    }
//
//
//                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    int id = mDataSet.size();
                    SampleSZ01 sample = new SampleSZ01("Sample" + id);//name
                    sample.setId(UUID.randomUUID().toString());//id
                    sample.setIndex("" + id);//index
                    sample.setBarCode(edtBarCode.getText().toString());//bcode


                    mDataSet.add(id, sample);
                    mAdapter.notifyItemInserted(id);
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    Toast.makeText(getContext(), "ITEM " + id + " Added!", Toast.LENGTH_SHORT).show();
                    //find next free bcode
//                    edtBarCode.setText(findFirstFreeInCache());
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = mDataSet.size() - 1;
                int position = currentPos;
                if (position >= 0 && mDataSet.size() != 0) {
                    mAdapter.setSelected(position - 1);
                    mDataSet.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mRecyclerView.scrollToPosition(position - 1);
                    Toast.makeText(getContext(), "Item. .." + position + " Deleted!", Toast.LENGTH_SHORT).show();

                    updateBarcode(position);
                }

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

//        initUI();


        return v;
    }

    public void initUI(){
//        find first free code
        edtBarCode.setText(findFirstFreeInCache());
    }


    public void updateBarcode(int pos) {
//        realm = Realm.getInstance(getActivity());
//        BarCode newCode=realm.where(BarCode.class).equalTo("index", ""+pos). findFirst();
//        realm.beginTransaction();
//        if (newCode != null) {
//            // set the fields he
//            newCode.setUsed(false);
//        }
//        realm.commitTransaction();


//        int searchListLength = codesList.size();
//        for (int i = 0; i < searchListLength; i++) {
//            if (codesList.get(i).getIndex() == pos) {
//                codesList.get(i).setUsed(false);
//                codesList.get(i).setSid("0");
//            }
//        }

    }

    public String findFirstFreeInCache() {
        String reslut = "no";
//        if(codeMapNoUsed.size()>0){
//            for(Map.Entry<String,BarCode> entry : codeMapNoUsed.entrySet()) {
//                String key = entry.getKey();
//                BarCode value = entry.getValue();
//                codeMapUsed.put(key,value);
//                codeMapNoUsed.remove(key);
//                reslut=value.getCode();
//            }
        if (codeMapNoUsed.size() > 0) {
            Random random = new Random();
            List<String> keys = new ArrayList<>(codeMapNoUsed.keySet());
            String randomKey = keys.get(random.nextInt(keys.size()));
            Toast.makeText(getContext(), "first unused code"+randomKey, Toast.LENGTH_SHORT).show();
            BarCode code = codeMapNoUsed.get(randomKey);
            codeMapUsed.put(randomKey, code);
            codeMapNoUsed.remove(randomKey);
        } else
            Toast.makeText(getContext(), "first unused code-no", Toast.LENGTH_SHORT).show();
        return reslut;
    }


    public int listUnusedBarcode() {
        realm = Realm.getInstance(getActivity());
        RealmResults<BarCode> barCodes =
                realm.where(BarCode.class).equalTo("used", false)
                        .findAll();
//        Toast.makeText(getContext(), "list-where sid=0,unused:" + barCodes.size(), Toast.LENGTH_SHORT).show();
        for (BarCode item : barCodes) {
            codeMapNoUsed.put(item.getCode(), item);
        }
        Toast.makeText(getContext(), "Free code in cache:" + codeMapNoUsed.size(), Toast.LENGTH_SHORT).show();
        return barCodes.size();

//        mDataSet.clear();
//        int index = 0;
//        for (BarCode item : barCodes) {
//            //   Toast.makeText(getContext(), "list[0]:"+item.getId()+"/bc:"+item.getbCode()+"/us:"+item.isUsed()+"/sid:"+item.getSid(), Toast.LENGTH_SHORT).show();
//            SampleSZ01 sample = new SampleSZ01("SZ01" + index);//name
//            sample.setId(item.getSampleId());//UUID
//            sample.setBarCode(item.getCode());//Barcode
//            sample.setIndex("" + index);
//
//            mDataSet.add(sample);
//            codesList.add(item);
//            codesStrList.add(item.getCode());
//            index++;
//        }
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

    public void updateDetails(int position) {
        currentPos = position;
        edtBarCode.setText("样本" + (position + 1) + "/" + iSampl + "(共" + iSampl + "个样本)");

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
        realm.close();
    }

}