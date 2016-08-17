package com.xeeshi.nsoft.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.xeeshi.nsoft.BuildConfig;

import java.util.Calendar;

/**
 * Created by ZEESHAN on 15/08/16.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private GetSelectedDate getSelectedDate;

    private String date;

    public DatePickerFragment() {

    }

    @SuppressLint("ValidFragment")
    public DatePickerFragment(Fragment fragment, String date) {

        this.date = date;
        // registering interface with host fragment otherwise throw exception
        try {
            getSelectedDate = (GetSelectedDate) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement GetSelectedDate");
        }
    }

    @SuppressLint("ValidFragment")
    public DatePickerFragment(Activity activity, String date) {

        this.date = date;
        // registering interface with host activity otherwise throw exception
        try {
            getSelectedDate = (GetSelectedDate) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement GetSelectedDate");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = 0, month = 0, day = 0;
        boolean selectedDate = false;

        Dialog dialog;
        try {
            if (null!=date && date.length()>0) {
                String[] splitDate = date.split(" - ");
                if (splitDate.length==3) {
                    year = Integer.parseInt(splitDate[0]);
                    day = Integer.parseInt(splitDate[1]);
                    month = Integer.parseInt(splitDate[2]);
                    month--;
                    selectedDate = true;
                }
            }

            if (!selectedDate) {
                // Use the current date as the default date in the picker
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }

            // Create a new instance of DatePickerDialog and return it
            dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        }


        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (BuildConfig.DEBUG)
            Log.e("DATE", "Day " + day + " Month " + month + " Year " + year );

        // Sending date to activity or fragment where GetSelectedDate interface is implemented
        if (null!=getSelectedDate)
            getSelectedDate.getDate(view, year, month+1, day);
    }

    /**
     * This interface must be implemented by activities or fragments that contain this
     * fragment to get selected date on user selection.
     */
    public interface GetSelectedDate {
        void getDate(DatePicker view, int year, int month, int day);
    }

}