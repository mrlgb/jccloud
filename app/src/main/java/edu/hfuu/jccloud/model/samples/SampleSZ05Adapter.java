package edu.hfuu.jccloud.model.samples;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;

public class SampleSZ05Adapter extends RecyclerView.Adapter<SampleSZ05Adapter.ViewHolder> {
    private ArrayList<SampleSZ05> mSampleList;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.locationTextView)
        TextView locationTextView;
        @Bind(R.id.timeTextView)
        TextView timeTextView;
        @Bind(R.id.codeTextView)
        TextView codeTextView;
        @Bind(R.id.list_row)
        RelativeLayout list_row;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
    public void add(int position, SampleSZ05 item) {
        mSampleList.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(String item) {
        int position = mSampleList.indexOf(item);
        mSampleList.remove(position);
        notifyItemRemoved(position);
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public SampleSZ05Adapter(ArrayList<SampleSZ05> sampleSZ01List, Context context) {
        mSampleList = sampleSZ01List;
        mPref = context.getSharedPreferences("SampleSZ05", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }
    // Create new views (invoked by the layout manager)
    @Override
    public SampleSZ05Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sz_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.locationTextView.setText("位置:"+mSampleList.get(position).getLocation());
        holder.timeTextView.setText("时间:"+mSampleList.get(position).getSampleTime());
        holder.codeTextView.setText("条形码:"+mSampleList.get(position).getBarCode());
//        Log.e("++++++selection1+++++/", "" +position+"/"+ mSampleList.get(position).isSelected());
        if (mSampleList.get(position).isSelected()) {
            holder.list_row.setBackgroundColor(Color.CYAN);
        } else {
            holder.list_row.setBackgroundColor(Color.TRANSPARENT);
        }
    }
    public void setSelected(int pos) {
        try {
            Log.e("++++++selection2++++/", pos+"/" + mSampleList.size());
            if (mSampleList.size() > 1) {
                mSampleList.get(mPref.getInt("SampleSZ05", 0)).setSelected(false);
                mEditor.putInt("SampleSZ05", pos);
                mEditor.commit();
            }
            mSampleList.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSampleList.size();
    }
}