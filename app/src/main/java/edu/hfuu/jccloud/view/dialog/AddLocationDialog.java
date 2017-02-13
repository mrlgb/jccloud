package edu.hfuu.jccloud.view.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;

/**
 * Created by roma on 05.11.15.
 */
public class AddLocationDialog extends DialogFragment implements View.OnClickListener {
    private List<String> locationList;
    private OnAddLocationListener listener;

    @Bind(R.id.spinneNewLocation)
    Spinner spinLocation;

    @Bind(R.id.bt_select_location)
    Button btSelectLocation;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_location, container);
        ButterKnife.bind(this, view);
        btSelectLocation.setOnClickListener(this);

        locationList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            locationList.add(0, "南大门-" + i);
        }
        for (int i = 0; i < 5; i++) {
            locationList.add(0, "北大门-" + i);
        }

        initSpinnerData(spinLocation, locationList);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_location: {
                if (spinLocation.getSelectedItem() != null)
                    listener.onAddLocationClickListener(spinLocation.getSelectedItem().toString());
                else
                    listener.onAddLocationClickListener("");
                break;
            }
        }
    }

    public void setListener(OnAddLocationListener listener) {
        this.listener = listener;
    }

    public interface OnAddLocationListener {
        void onAddLocationClickListener(String location);
    }

    private void initSpinnerData(Spinner spinner, List<String> list) {
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(), R.layout.spinner_item, list) {
            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
