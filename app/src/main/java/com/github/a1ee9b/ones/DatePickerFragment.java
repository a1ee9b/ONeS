package com.github.a1ee9b.ones;

/**
 * Created by Jannik on 21.12.2015.
 */

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int year = sharedPref.getInt(getString(R.string.year), -1);
        int month = sharedPref.getInt(getString(R.string.month), -1);
        int day = sharedPref.getInt(getString(R.string.day), -1);

        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int cyear = (year < 0)? c.get(Calendar.YEAR) : year;
        int cmonth = (month < 0)? c.get(Calendar.MONTH) : month;
        int cday = (day < 0)? c.get(Calendar.DAY_OF_MONTH) : day;

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
        return new DatePickerDialog(getActivity(), this, cyear, cmonth, cday);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Do something with the date chosen by the user
        Log.d(TAG, "Datum: " + day + "." + month + "." + year);

//        SharedPreferences settings = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
//
//        //set the sharedpref
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt("ID", "1");
//        editor.commit();

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.year), year);
        editor.putInt(getString(R.string.month), month);
        editor.putInt(getString(R.string.day), day);
        editor.commit();
    }
}