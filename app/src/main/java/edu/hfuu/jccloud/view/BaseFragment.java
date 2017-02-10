package edu.hfuu.jccloud.view;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by lgb on 21-11-2016.
 */
public abstract class BaseFragment extends Fragment {

    abstract protected void initComponents();

    public void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    /**
     * Add padding to numbers less than ten
     */
    protected static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }


}