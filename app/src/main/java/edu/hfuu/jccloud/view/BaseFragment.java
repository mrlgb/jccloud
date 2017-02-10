package edu.hfuu.jccloud.view;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by lgb on 21-11-2016.
 */
public abstract class BaseFragment extends Fragment {
    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}