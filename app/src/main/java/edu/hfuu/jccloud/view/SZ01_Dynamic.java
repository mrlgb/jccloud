package edu.hfuu.jccloud.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.globalCodes;
import edu.hfuu.jccloud.model.sampleSZ.SampleSZ01;
import edu.hfuu.jccloud.model.sampleSZ.SampleSZ01Adapter;
import io.realm.Realm;
import io.realm.RealmResults;

import static edu.hfuu.jccloud.R.id.my_recycler_view;
import static edu.hfuu.jccloud.model.globalCodes.plants;

/**
 * Created by lgb on 21-11-2016.
 */
public class SZ01_Dynamic extends Fragment {
    private  int SAMPLESIZE=5;
    private ArrayList<SampleSZ01> mDataSet;
    private SampleSZ01Adapter mAdapter;
    private List<String> codesList;


    @Bind(my_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.spinnerSampleId)
    Spinner sSpinnerId;


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

    int mHour, mMinute, mSecond;
    Realm realm;

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
        listBarcode();
        mDataSet = new ArrayList<>();
        loadData();
        mAdapter = new SampleSZ01Adapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);


//        // Initializing an ArrayAdapter
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                getContext(),R.layout.spinner_item,plants);
//        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        sSpinnerId.setAdapter(spinnerArrayAdapter);

        globalCodes g= new globalCodes();
        // TODO: 2017/2/6
        //all binary codes ++++ no covered relationship between sample and binarycode xxxxxxxxxxxxxxx
        // TODO: 2017/2/6
        codesList = new ArrayList<String>(g.getsCache().keySet());

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.spinner_item,codesList){
            @Override
            public boolean isEnabled(int position){
                if(findIndexUseable(position))
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(findIndexUseable(position)) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sSpinnerId.setAdapter(spinnerArrayAdapter);


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
                int id = mDataSet.size();
                SampleSZ01 sample = new SampleSZ01(""+id, "sample " + id);
                mDataSet.add(id, sample);
                mAdapter.notifyItemInserted(id);
                mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                Toast.makeText(getContext(), "Sample " + id + " Added!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mDataSet.size() - 1;
                if(position>=0){
                    mAdapter.setSelected(position-1);
                    mDataSet.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mRecyclerView.scrollToPosition(position - 1);
                    Toast.makeText(getContext(), "Sample " + position + " Deleted!", Toast.LENGTH_SHORT).show();
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

        return v;
    }

    public void listBarcode( ) {
        realm = Realm.getInstance(getActivity());
        RealmResults<BarCode> barCodes = realm.where(BarCode.class).equalTo("sid","0").findAll();
//        for (BarCode iem:barCodes) {
//            Toast.makeText(getContext(), "list[0]:"+iem.getId()+"/bc:"+iem.getbCode()+"/us:"+iem.isUsed()+"/sid:"+iem.getSid(), Toast.LENGTH_SHORT).show();
//
//        }
        Toast.makeText(getContext(), "listSize[0]:"+barCodes.size(), Toast.LENGTH_SHORT).show();

    }


    private boolean validateData() {
        boolean result = true;

        String time = edtTime.getText().toString();

        if (time == null || time.length() < 3) {
            // We set the error message
            inputLayoutTime.setError("请输入正确的时间（）");
            result = false;
        } else
            // We remove error messages
            inputLayoutTime.setErrorEnabled(false);

        return result;
    }

    public void updateDetails(int position) {
//        input11.getEditText().setText(mDataSet.get(position).getIndex());
//        inputSampleNo.getEditText().setText(mDataSet.get(position).getIndex());
//        input21.getEditText().setText(mDataSet.get(position).getDes());
        // TODO: 2017/2/6
        //search position correspond binarycode;
        // TODO: 2017/2/6
        sSpinnerId.setSelection(position);
        sSpinnerId.setSelected(false);

    }

    /** Add padding to numbers less than ten */
    private static String pad ( int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


    // load initial data
    public void loadData() {
        mDataSet.clear();
        for (int i = 0; i < SAMPLESIZE; i++) {
            SampleSZ01 sample = new SampleSZ01(""+i, "sample " + i);
            mDataSet.add(sample);
        }
    }

    private boolean findIndexUseable(int pos) {
        //TO DO 判断ID是否可用？
        return (plants[pos].contains("20170211111"));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        realm.close();
    }

}