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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.hfuu.jccloud.R;
import edu.hfuu.jccloud.model.BarCode;
import edu.hfuu.jccloud.model.SZ01.SampleSZ01;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by roma on 05.11.15.
 */
public class AddBarCodeDialog extends DialogFragment implements View.OnClickListener {
    private Realm realm;
    private List<String> codesStrList;

    private int dataSize=0;

    @Bind(R.id.spinneNewId)
    Spinner spinBarcode;

    @Bind(R.id.bt_select_barcode)
    Button btSelectBarcode;

    private OnAddBarCodeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_barcode, container);
        initComponents(view);
        ButterKnife.bind(this, view);


        codesStrList = new ArrayList<>();
        dataSize = listUsedBarcode();
        initSpinnerUI(spinBarcode, codesStrList);

        return view;
    }

    private void initComponents(View view) {
        spinBarcode = (Spinner) view.findViewById(R.id.spinneNewId);
//        spinBarcode.requestFocus();
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btSelectBarcode = (Button) view.findViewById(R.id.bt_select_barcode);
        btSelectBarcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_barcode: {
                listener.onAddBarCodeClickListener(spinBarcode.getId());
                break;
            }
        }
    }

    public void setListener(OnAddBarCodeListener listener) {
        this.listener = listener;
    }

    public interface OnAddBarCodeListener {
        void onAddBarCodeClickListener(int barCodeId);
    }

    public int listUsedBarcode() {
        realm = Realm.getInstance(getActivity());
        RealmResults<BarCode> barCodes =
                realm.where(BarCode.class).equalTo("used", false)
                        .findAll();
        Toast.makeText(getContext(), "可用条形码个数:" + barCodes.size(), Toast.LENGTH_SHORT).show();

        int index = 0;
        for (BarCode item : barCodes) {
            //   Toast.makeText(getContext(), "list[0]:"+item.getId()+"/bc:"+item.getbCode()+"/us:"+item.isUsed()+"/sid:"+item.getSid(), Toast.LENGTH_SHORT).show();
            SampleSZ01 sample = new SampleSZ01("SZ01" + index);//name
            sample.setId(item.getId());//UUID
            sample.setBarCode(item);//Barcode
            sample.setIndex("" + index);
            codesStrList.add(item.getbCode());
            index++;
        }

        return barCodes.size();
    }

    private void initSpinnerUI(Spinner spinner, List<String> list) {
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
