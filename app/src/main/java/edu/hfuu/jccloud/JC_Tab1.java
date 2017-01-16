package edu.hfuu.jccloud;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.util.Attributes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.tableUi.DividerItemDecoration;
import edu.hfuu.jccloud.tableUi.underwater.EventSample;
import edu.hfuu.jccloud.tableUi.underwater.SwipeRecyclerViewAdapter;
import edu.hfuu.jccloud.tableUi.underwater.UnderwaterSample;

import static edu.hfuu.jccloud.R.id.my_recycler_view;

/**
 * Created by lgb on 21-01-2015.
 */
public class JC_Tab1 extends Fragment {

    private ArrayList<UnderwaterSample> mDataSet;
    //    private TextView tvEmptyView;
    //    private RecyclerView mRecyclerView;
    @Bind(my_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.inputLayout1)
    TextInputLayout _inputLayout1;
    @Bind(R.id.inputLayout2)
    TextInputLayout _inputLayout2;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.jc_layout1, container, false);
        //tvEmptyView = (TextView) v.findViewById(R.id.empty_view);
//        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
//
        ButterKnife.bind(this, v);
        //注册
        EventBus.getDefault().register(this);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());


        mDataSet = new ArrayList<UnderwaterSample>();

        loadData();

        if (mDataSet.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            // tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            //tvEmptyView.setVisibility(View.GONE);
        }

        // Creating Adapter object
        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(getActivity(), mDataSet);


        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);


        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return v;
    }


    // This method will be called when a EventStudent  is  posted
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventSample event){
//        Toast.makeText(getActivity(), "ding!@"+event.getEventStuduent().getName(), Toast.LENGTH_SHORT).show();
        _inputLayout1.getEditText().setText(event.getEventSample().getIndex());
        _inputLayout2.getEditText().setText(event.getEventSample().getId());
    }

    // load initial data
    public void loadData() {

        for (int i = 0; i <= 20; i++) {
            mDataSet.add(new UnderwaterSample("Underwater " + i, "Underwater" + i + "@jcclcoud.com"));

        }


    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}