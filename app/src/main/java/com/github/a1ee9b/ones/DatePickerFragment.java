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
    private static String TAG = "ONeS - DatePickerFragment";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ONeS_Calender", Context.MODE_PRIVATE);
        int savedYear = sharedPreferences.getInt("year", -1);
        int savedMonth = sharedPreferences.getInt("month", -1);
        int savedDay = sharedPreferences.getInt("day", -1);

        final Calendar calender = Calendar.getInstance();
        int year = (savedYear < 0)? calender.get(Calendar.YEAR) : savedYear;
        int month = (savedMonth < 0)? calender.get(Calendar.MONTH) : savedMonth;
        int day = (savedDay < 0)? calender.get(Calendar.DAY_OF_MONTH) : savedDay;

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("year", year);
        editor.putInt("month", month);
        editor.putInt("day", day);
        editor.apply();
    }
}