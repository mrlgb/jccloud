package edu.hfuu.jccloud.tableUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.hfuu.jccloud.R;

/**
 * Created by lgb on 21-01-2015.
 */
public class SZ01_Static extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz_01_static,container,false);
        return v;
    }
}