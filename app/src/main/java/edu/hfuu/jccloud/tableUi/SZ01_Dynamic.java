package edu.hfuu.jccloud.tableUI;

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
import android.widget.Toast;

import java.util.ArrayList;

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
    @Bind(R.id.inputLayout1)
    TextInputLayout input1;
    @Bind(R.id.inputLayout2)
    TextInputLayout input2;
    @Bind(R.id.inputLayout3)
    TextInputLayout input3;
    @Bind(R.id.buttonAdd)
    Button btnAdd;
    @Bind(R.id.buttonDelete)
    Button btnDel;
    @Bind(R.id.buttonSave)
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz_01_dynamic, container, false);

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mDataSet.size();
                SampleSZ01 sample = new SampleSZ01("Sample " + id, "ID " + id, "index " + id);
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
                mRecyclerView.scrollToPosition(position-1);
                Toast.makeText(getContext(), "Sample " + position + " Deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "All sample have been saved!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void updateDetails(int position) {
        input1.getEditText().setText(mDataSet.get(position).getId());
        input2.getEditText().setText(mDataSet.get(position).getIndex());
        input3.getEditText().setText(mDataSet.get(position).getDesc());

    }


    // load initial data
    public void loadData() {
        mDataSet.clear();
        for (int i = 0; i < 25; i++) {
            SampleSZ01 sample = new SampleSZ01("Sample " + i, "ID " + i, "index " + i);
            mDataSet.add(sample);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}