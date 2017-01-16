package edu.hfuu.jccloud.tableUi.underwater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {


    private Context mContext;
    private ArrayList<UnderwaterSample> smapleList;

    public SwipeRecyclerViewAdapter(Context context, ArrayList<UnderwaterSample> objects) {
        this.mContext = context;
        this.smapleList = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_row_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final UnderwaterSample item = smapleList.get(position);

        viewHolder.tvIndex.setText((item.getIndex()) + "  -  Row Position " + position);
        viewHolder.tvId.setText(item.getId());


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));


        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });



        viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((((SwipeLayout) v).getOpenStatus() == SwipeLayout.Status.Close)) {
                    //Start your activity

                    //Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new UnderwaterSample(item.getIndex(),item.getId()));
                //Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                smapleList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, smapleList.size());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.tvIndex.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return smapleList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.swipe)  SwipeLayout swipeLayout;
        @Bind(R.id.tvIndex) TextView tvIndex;
        @Bind(R.id.tvId) TextView tvId;
        @Bind(R.id.tvDelete) TextView tvDelete;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
