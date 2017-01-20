package edu.hfuu.jccloud.SampleSZ;

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

import edu.hfuu.jccloud.R;

public class SampleSZ01Adapter extends RecyclerView.Adapter<SampleSZ01Adapter.ViewHolder> {
    private ArrayList<SampleSZ01> mSampleSZ01List;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView personNameTextView, personAddTextView, personDsgTextView;
        RelativeLayout list_row;
        public ViewHolder(View v) {
            super(v);
            personNameTextView = (TextView) v.findViewById(R.id.personNameTextView);
            personAddTextView = (TextView) v.findViewById(R.id.personAddTextView);
            personDsgTextView = (TextView) v.findViewById(R.id.personDsgTextView);
            list_row = (RelativeLayout) v.findViewById(R.id.list_row);
        }
    }
    public void add(int position, SampleSZ01 item) {
        mSampleSZ01List.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(String item) {
        int position = mSampleSZ01List.indexOf(item);
        mSampleSZ01List.remove(position);
        notifyItemRemoved(position);
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public SampleSZ01Adapter(ArrayList<SampleSZ01> sampleSZ01List, Context context) {
        mSampleSZ01List = sampleSZ01List;
        mPref = context.getSharedPreferences("person", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }
    // Create new views (invoked by the layout manager)
    @Override
    public SampleSZ01Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list_item_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.personNameTextView.setText(mSampleSZ01List.get(position).getId());
        holder.personAddTextView.setText(mSampleSZ01List.get(position).getIndex());
        holder.personDsgTextView.setText(mSampleSZ01List.get(position).getDsg());
        Log.e("selection", "" + mSampleSZ01List.get(position).isSelected());
        if (mSampleSZ01List.get(position).isSelected()) {
            holder.list_row.setBackgroundColor(Color.parseColor("#3f7ce4"));
        } else {
            holder.list_row.setBackgroundColor(Color.TRANSPARENT);
        }
    }
    public void setSelected(int pos) {
        try {
            if (mSampleSZ01List.size() > 1) {
                mSampleSZ01List.get(mPref.getInt("position", 0)).setSelected(false);
                mEditor.putInt("position", pos);
                mEditor.commit();
            }
            mSampleSZ01List.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSampleSZ01List.size();
    }
}