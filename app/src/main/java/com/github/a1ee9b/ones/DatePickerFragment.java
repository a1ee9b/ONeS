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
        int year = sharedPref.getInt("year", -1);
        int month = sharedPref.getInt("month", -1);
        int day = sharedPref.getInt("day", -1);

        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int cyear = (year < 0)? c.get(Calendar.YEAR) : year;
        int cmonth = (month < 0)? c.get(Calendar.MONTH) : month;
        int cday = (day < 0)? c.get(Calendar.DAY_OF_MONTH) : day;

        return new DatePickerDialog(getActivity(), this, cyear, cmonth, cday);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("year", year);
        editor.putInt("month", month);
        editor.putInt("day", day);
        editor.commit();
    }
}