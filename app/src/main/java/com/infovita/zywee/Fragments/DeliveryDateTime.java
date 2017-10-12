package com.infovita.zywee.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.infovita.zywee.R;
import com.infovita.zywee.Supports.constant;
import com.infovita.zywee.stepper.stepperFragment;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Khizarkhan on 03-02-2017.
 */
public class DeliveryDateTime extends stepperFragment implements View.OnClickListener {

    public static final String ORDER = "OrderData";

    RelativeLayout datePicker, timePicker;
    ImageButton dateButton,timeButton;
    static TextView zDateTextView;
    static TextView zTimeTextView;

    DatePicker date_picker;
    private String TAG = "DeliveryDateTime";

    public DeliveryDateTime() {
        // Required empty public constructor
    }




    @Override
    public boolean onNextButtonHandler() {
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(ORDER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            editor.putString(constant.date, zDateTextView.getText().toString().trim());
            editor.putString(constant.time, zTimeTextView.getText().toString().trim());
            editor.apply();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onBackButtonHandler() {
        return true;
    }


    View rootView;


    @SuppressLint("NewApi")
    private void initialise() {

        datePicker = (RelativeLayout) rootView.findViewById(R.id.date_picker);
        timePicker = (RelativeLayout) rootView.findViewById(R.id.time_picker);
        dateButton = (ImageButton) rootView.findViewById(R.id.date_button);
        timeButton = (ImageButton) rootView.findViewById(R.id.time_button);

        zDateTextView = (TextView) rootView.findViewById(R.id.delivery_date);
        zTimeTextView = (TextView) rootView.findViewById(R.id.delivery_start_time);

        date_picker = (DatePicker) rootView.findViewById(R.id.datePicker);
//        date_picker.setMinDate(new Date().getTime());

        setCurrentDateTime();

        datePicker.setOnClickListener(this);
        timePicker.setOnClickListener(this);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    static Date currentDate;
    static String time;

    private void setCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        currentDate = c.getTime();
        //c.add(Calendar.HOUR, 2);  //Add extra 2 hours to the calendar
        SimpleDateFormat delivery_date = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat start_time = new SimpleDateFormat("hh:mm a");
        String date = delivery_date.format(c.getTime());
        time = start_time.format(c.getTime());
        update(date, time);
    }





    /**
     * Set the text view with date and time
     *
     * @param date Date
     * @param time Time
     */
    private void update(String date, String time) {
        zDateTextView.setText(date);
        zTimeTextView.setText(time);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getChildFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getChildFragmentManager(), "datePicker");
    }


    static RelativeLayout mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_delivery_date_time, container, false);
        mRoot = (RelativeLayout) rootView.findViewById(R.id.delivery_date_time_layout);
        initialise();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_button:
            case R.id.date_picker:
                showDatePickerDialog(v);
                break;

            case R.id.time_button:

            case R.id.time_picker:
                showTimePickerDialog(v);
                break;
        }
    }


    static Date dateSelected;
    static int selectedMonth, selectedDay, selectedYear;

    @SuppressLint("NewApi")
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        Calendar c;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public String getMonth(int month) {
            return new DateFormatSymbols().getMonths()[month];
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String monthString = getMonth(month);

            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, day);
            // long today = cal.getTimeInMillis();
            dateSelected = newDate.getTime();
            // compareTo function is used to compare two dates
            // -1 if dateSelected is sooner than today
            // 0 if dateSelected and today dates are equal
            // 1 if dateSelected is after today's date
            if (dateSelected.compareTo(currentDate) == -1) {
                /*SharedData sd = new SharedData();
                sd.snakbarMessage(mRoot.findViewById(android.R.id.content).getRootView(), "Please select valid date");*/
                Toast.makeText(getContext(), "Please select valid date", Toast.LENGTH_SHORT).show();
            } else {
                selectedDay = day;
                selectedMonth = month;
                selectedYear = year;
                zDateTextView.setText(monthString + " " + String.valueOf(day) + ", " + String.valueOf(year));
                zTimeTextView.setText(time);
            }
        }

    }



    @SuppressLint("NewApi")
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        Calendar c;
        long startNewTimeInMilli;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            final String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);


            c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH,
                    selectedDay);
            c.set(Calendar.MONTH, selectedMonth);
            c.set(Calendar.YEAR, selectedYear);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);

            Date timeSelected = c.getTime();

            startNewTimeInMilli = c.getTimeInMillis();


            if (timeSelected.compareTo(currentDate) == -1) {
//                errorString = "Please select time after the current time";
                Toast.makeText(getContext(), "Please select valid time", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(time);
                    zTimeTextView.setText(new SimpleDateFormat("hh:mm a").format(dateObj));
                } catch (final ParseException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}

