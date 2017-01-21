package edu.hfuu.jccloud.tableUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;

/**
 * Created by lgb on 21-01-2015.
 */
public class SZ01_Static extends Fragment {
    @Bind(R.id.btn_SZ_O1_Static_Save)
    Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz01_static,container,false);
        ButterKnife.bind(this, v);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "All data have been saved!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}