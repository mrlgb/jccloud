package edu.hfuu.jccloud.tableUI;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.sampleSZ.SampleSZ01;
import edu.hfuu.jccloud.sampleSZ.SampleSZ01Adapter;

import static edu.hfuu.jccloud.R.id.my_recycler_view;

/**
 * Created by lgb on 21-11-2016.
 */
public class SZ01_Dynamic extends Fragment {
    private ArrayList<SampleSZ01> mDataSet;
    private SampleSZ01Adapter mAdapter;
    @Bind(my_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.inputLayout11)
    TextInputLayout input11;
    @Bind(R.id.SampleNoLayout12)
    TextInputLayout inputSampleNo;
    @Bind(R.id.edtSampleNo)
    EditText edtSampleNo;
    @Bind(R.id.inputLayout21)
    TextInputLayout input21;

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
                            mAdapter.setSelected(position);
                            updateDetails(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );

        mDataSet = new ArrayList<>();

        //
        loadData();
        mAdapter = new SampleSZ01Adapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);

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
                                                .append(pad(mHour)).append(":")
                                                .append(pad(mMinute)));
                            }
                        }, 0, 0, true);
                        timePickerDialog.show();
                    }


                });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mDataSet.size();
                SampleSZ01 sample = new SampleSZ01("Sample " + id, "index " + id);
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
                mDataSet.remove(position);
                mAdapter.notifyItemRemoved(position);
                mRecyclerView.scrollToPosition(position - 1);
                Toast.makeText(getContext(), "Sample " + position + " Deleted!", Toast.LENGTH_SHORT).show();
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
        boolean result = true;

        String name = edtSampleNo.getText().toString();
        if (name == null || name.length() < 3) {
            // We set the error message
            inputSampleNo.setError("请输入正确的样品编号（>3字符）");
            result = false;
        } else
            // We remove error messages
            inputSampleNo.setErrorEnabled(false);

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
        input11.getEditText().setText(mDataSet.get(position).getIndex());
        inputSampleNo.getEditText().setText(mDataSet.get(position).getIndex());
        input21.getEditText().setText(mDataSet.get(position).getDes());

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
        for (int i = 0; i < 25; i++) {
            SampleSZ01 sample = new SampleSZ01("Sample " + i, "index " + i);
            mDataSet.add(sample);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}