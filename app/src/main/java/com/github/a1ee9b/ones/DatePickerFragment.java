package com.github.a1ee9b.ones;

/**
 * Created by Jannik on 21.12.2015.
 */

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static String TAG = "DatePickerFragment";
    private int day = -1;
    private int month = -1;
    private int year = -1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = (this.year < 0)? c.get(Calendar.YEAR) : this.year;
        int month = (this.month < 0)? c.get(Calendar.MONTH) : this.month;
        int day = (this.day < 0)? c.get(Calendar.DAY_OF_MONTH) : this.day;

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        Log.d(TAG, "Datum: " + day + "." + month + "." + year);

        this.day = day;
        this.month = month;
        this.year = year;
    }
}