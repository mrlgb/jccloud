package edu.hfuu.jccloud.model.SZ01;

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

public class SampleSZ01Adapter extends RecyclerView.Adapter<SampleSZ01Adapter.ViewHolder> {
    private ArrayList<SampleSZ01> mSampleList;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.indexTextView)
        TextView indexTextView;
        @Bind(R.id.idTextView)
        TextView idTextView;
        @Bind(R.id.descTextView)
        TextView descTextView;
        @Bind(R.id.list_row)
        RelativeLayout list_row;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
    public void add(int position, SampleSZ01 item) {
        mSampleList.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(String item) {
        int position = mSampleList.indexOf(item);
        mSampleList.remove(position);
        notifyItemRemoved(position);
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public SampleSZ01Adapter(ArrayList<SampleSZ01> sampleSZ01List, Context context) {
        mSampleList = sampleSZ01List;
        mPref = context.getSharedPreferences("person", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }
    // Create new views (invoked by the layout manager)
    @Override
    public SampleSZ01Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sz01_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.indexTextView.setText(mSampleList.get(position).getIndex());
        holder.idTextView.setText(mSampleList.get(position).getId());
        holder.descTextView.setText(mSampleList.get(position).getBarCode().getbCode());
        Log.e("++++++selection1+++++/", "" +position+"/"+ mSampleList.get(position).isSelected());
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
                mSampleList.get(mPref.getInt("position", 0)).setSelected(false);
                mEditor.putInt("position", pos);
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