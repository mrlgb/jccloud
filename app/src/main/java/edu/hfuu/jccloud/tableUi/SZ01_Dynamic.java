package edu.hfuu.jccloud.tableUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.SampleSZ.SampleSZ01Adapter;
import edu.hfuu.jccloud.SampleSZ.SampleSZ01;

import static edu.hfuu.jccloud.R.id.my_recycler_view;

/**
 * Created by lgb on 21-01-2015.
 */
public class SZ01_Dynamic extends Fragment {
    private ArrayList<SampleSZ01> mDataSet;
    private SampleSZ01Adapter mAdapter;
    @Bind(my_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz_01_dynamic, container, false);

        ButterKnife.bind(this, v);
//        //注册
//        EventBus.getDefault().register(this);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            mAdapter.setSelected(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );

        mDataSet = new ArrayList<>();

        loadData();
        mAdapter = new SampleSZ01Adapter(mDataSet,getContext());
        mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                Log.e("RecyclerView", "onScrollStateChanged");
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        return v;
    }


    // load initial data
    public void loadData() {
        mDataSet.clear();
        for (int i = 0; i < 25; i++) {
            SampleSZ01 sample = new SampleSZ01("Sample " + i, "ID " + i, "index " + i);
            mDataSet.add(sample);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
//        EventBus.getDefault().unregister(this);
    }

}