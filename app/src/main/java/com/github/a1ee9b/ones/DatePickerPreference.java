package com.github.a1ee9b.ones;

import android.app.DatePickerDialog;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Jannik on 22.12.2015.
 */
public class DatePickerPreference extends DialogPreference implements DatePickerDialog.OnDateSetListener {
    private static String TAG = "DatePickerPreference";
    private int day = -1;
    private int month = -1;
    private int year = -1;

    public DatePickerPreference(Context context) {
        super(context);

        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = (this.year < 0) ? c.get(Calendar.YEAR) : this.year;
        int month = (this.month < 0) ? c.get(Calendar.MONTH) : this.month;
        int day = (this.day < 0) ? c.get(Calendar.DAY_OF_MONTH) : this.day;

        new DatePickerDialog(context, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG, "Datum: " + day + "." + month + "." + year);
    }
}
