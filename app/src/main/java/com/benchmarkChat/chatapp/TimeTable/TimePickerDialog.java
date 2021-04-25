package com.benchmarkChat.chatapp.TimeTable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.benchmarkChat.chatapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePickerDialog {

    AlertDialog.Builder builder;
    Activity activity;
    TimePicker timePicker;

    private int minMin;
    private int minHour;

    int hour, min;

    public TimePickerDialog(Activity activity, int hour, int min) {
        this.activity = activity;
        this.hour = hour;
        this.min = min;

        minMin = min;
        minHour = hour;

    }


    public void buildView(){
        final View builderView = activity.getLayoutInflater().inflate(R.layout.time_picker_layout, null);
        builder = new AlertDialog.Builder(activity);
        builder.setView(builderView);

        timePicker = builderView.findViewById(R.id.time_picker);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(minHour);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setMinute(minMin);
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                min = i1;
                hour = i;
            }
        });
    }

    public void create(){
        builder.create();
    }

    public void show(){
        builder.show();
    }

    public boolean checkTime(String startTime, String endTime) {

        String pattern = "HH:mm";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);

            if (date1 != null) {
                return date1.before(date2);
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

}
