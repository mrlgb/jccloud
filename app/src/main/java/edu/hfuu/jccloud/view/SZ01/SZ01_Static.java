package edu.hfuu.jccloud.view.SZ01;

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

//    @Bind(R.id.inputLayoutDate)
//    TextInputLayout inputLaDate;
//
//    @Bind(inputDatePicker)
//    EditText inputDate;

    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sz01_static, container, false);
        ButterKnife.bind(this, v);

//        inputDate.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        //To show current date in the datepicker
//                        final Calendar mcurrentDate = Calendar.getInstance();
//                        mYear = mcurrentDate.get(Calendar.YEAR);
//                        mMonth = mcurrentDate.get(Calendar.MONTH);
//                        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
//
//                        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                                // TODO Auto-generated method stub
//                    /*      Your code   to get date and time    */
//                                mcurrentDate.set(Calendar.YEAR, mYear);
//                                mcurrentDate.set(Calendar.MONTH, mMonth);
//                                mcurrentDate.set(Calendar.DAY_OF_MONTH, mDay);
//                                Calendar newDate = Calendar.getInstance();
//                                newDate.set(selectedyear, selectedmonth, selectedday);
//                                String myFormat = "dd/MMM/yyyy"; //In which you need put here
//                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CHINA);
//                                inputDate.setText(sdf.format(newDate.getTime()));
//                            }
//                        }, mYear, mMonth, mDay);
//                        mDatePicker.setTitle("选择日期");
//                        mDatePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "确定", mDatePicker);
//                        mDatePicker.show();
//                    }
//                });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    Toast.makeText(getContext(), "All sample have been saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    private boolean validateData() {
        boolean result = true;
//        String name = inputDate.getText().toString();
//
//        if (name == null || name.length() < 3) {
//            // We set the error message
//            inputLaDate.setError("请输入正确的日期（）");
//            result = false;
//        } else
//            // We remove error messages
//            inputLaDate.setErrorEnabled(false);

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}