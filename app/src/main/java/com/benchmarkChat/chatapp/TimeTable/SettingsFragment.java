package com.benchmarkChat.chatapp.TimeTable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.benchmarkChat.chatapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsDialogFragment settingsDialogFragment;
    String startTimeInitialize;
    int dataHolderPosition;
    Adapter adapter;
    DataHolder dataHolder;

    public SettingsFragment(String startTime, int dataHolderPosition, Adapter adapter, SettingsDialogFragment settingsDialogFragment) {
        this.startTimeInitialize = startTime;
        this.dataHolderPosition = dataHolderPosition;
        this.adapter = adapter;
        this.settingsDialogFragment = settingsDialogFragment;

        dataHolder = adapter.dataHolders[dataHolderPosition];
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String[] startTime = new String[1];
        final String[] endTime = new String[1];
        final String[] title = new String[1];
        final String[] subTitle = new String[1];
        final Boolean[] isRecess = {false};

        Preference startTimePreference = getPreferenceManager().findPreference("start_time");
        Preference endTimePreference = getPreferenceManager().findPreference("end_time");
        SwitchPreferenceCompat isRecessPreference = getPreferenceManager().findPreference("recess");
        final EditTextPreference titlePreference = getPreferenceManager().findPreference("title");
        final EditTextPreference subTitlePreference = getPreferenceManager().findPreference("sub_title");

        getParentFragment().getView().findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: Clicked" );
                title[0] = titlePreference.getText();
                subTitle[0] = subTitlePreference.getText();

                if(isRecess[0]) {
                    if (title[0] == null || subTitle[0] == null || startTime[0] == null || endTime[0] == null) {
                        Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        if(!title[0].isEmpty() && !subTitle[0].isEmpty()){

                        DataHolder dataHolderObject = new DataHolder();
                        dataHolderObject.setRecess(true);
                        dataHolderObject.setTitle(title[0]);
                        dataHolderObject.setSubTitle(subTitle[0]);
                        dataHolderObject.setStartTime(startTime[0]);
                        dataHolderObject.setEndTime(endTime[0]);

                        adapter.dataHolders[dataHolderPosition] = dataHolderObject;
                        adapter.notifyDataSetChanged();

                        Toast.makeText(getActivity(), "Created scheduled slot!", Toast.LENGTH_SHORT).show();
                        settingsDialogFragment.dismiss();
                        }
                        else{
                            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    if(startTime[0] == null || endTime[0] == null){
                        Toast.makeText(getActivity(), "Please fill start and end time!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Created recess slot!", Toast.LENGTH_SHORT).show();

                        DataHolder dataHolderObject = new DataHolder();
                        dataHolderObject.setRecess(false);

                        adapter.dataHolders[dataHolderPosition] = dataHolderObject;
                        dataHolderObject.setStartTime(startTime[0]);
                        dataHolderObject.setEndTime(endTime[0]);
                        adapter.notifyDataSetChanged();
                        settingsDialogFragment.dismiss();
                    }
                }
            }
        });

            isRecessPreference.setChecked(false);
            titlePreference.setText("");
            subTitlePreference.setText("");

        if(dataHolder != null){
            isRecessPreference.setChecked(dataHolder.getRecess());
            title[0] = dataHolder.getTitle();
            subTitle[0] = dataHolder.getSubTitle();
            startTime[0] = dataHolder.getStartTime();
            endTime[0] = dataHolder.getEndTime();
            isRecess[0] = dataHolder.getRecess();
            if(dataHolder.getRecess()){
                titlePreference.setText(dataHolder.getTitle());
                subTitlePreference.setText(dataHolder.getSubTitle());
            }
        }

        isRecessPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                isRecess[0] = (Boolean) newValue;
                return true;
            }
        });

        if (startTimePreference != null) {
            startTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {


                    String[] timeSplit = startTimeInitialize.split(":");

                    if(startTime[0] != null)
                        timeSplit = startTime[0].split(":");

                    int a = Integer.parseInt(timeSplit[0]);
                    int b = Integer.parseInt(timeSplit[1]);

                    if(timeSplit[0].equals("-1") && timeSplit[1].equals("-1")){
                        a = 0;
                        b = 0;
                    }
                    final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), a , b);
                    timePickerDialog.buildView();
                    timePickerDialog.create();
                    final String[] finalTimeSplit = startTimeInitialize.split(":");
                    timePickerDialog.builder.setTitle("Set Time").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(Integer.parseInt(finalTimeSplit[0]) != -1 && Integer.parseInt(finalTimeSplit[0]) != -1) {
                                if (timePickerDialog.checkTime(Integer.parseInt(finalTimeSplit[0]) + ":" + Integer.parseInt(finalTimeSplit[1]), timePickerDialog.hour + ":" + timePickerDialog.min)) {
                                    startTime[0] = timePickerDialog.hour + ":" + timePickerDialog.min;
                                    Toast.makeText(getActivity(), "Time Has Been Set", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Please select time greater than or equal to " + Integer.parseInt(finalTimeSplit[0]) + ":" + Integer.parseInt(finalTimeSplit[1]), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                startTime[0] = timePickerDialog.hour + ":" + timePickerDialog.min;
                                Toast.makeText(getActivity(), "Time Has Been Set", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("Cancel", null);
                    timePickerDialog.show();


                    return false;
                }
            });
        }

        if (endTimePreference != null) {
                endTimePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        if (startTime[0] != null) {

                            String[] timeSplit = startTime[0].split(":");

                            if(endTime[0] != null)
                                timeSplit = endTime[0].split(":");

                            final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
                            timePickerDialog.buildView();
                            timePickerDialog.create();
                            final String[] finalTimeSplit = timeSplit;
                            timePickerDialog.builder.setTitle("Set Time").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Integer.parseInt(finalTimeSplit[0]) != -1 && Integer.parseInt(finalTimeSplit[1]) != -1) {
                                        if (timePickerDialog.checkTime(Integer.parseInt(finalTimeSplit[0]) + ":" + Integer.parseInt(finalTimeSplit[1]), timePickerDialog.hour + ":" + timePickerDialog.min)) {
                                            Toast.makeText(getActivity(), "Time Has Been Set", Toast.LENGTH_SHORT).show();
                                            endTime[0] = timePickerDialog.hour + ":" + timePickerDialog.min;

                                        } else {
                                            Toast.makeText(getActivity(), "Please select time greater than or equal to " + Integer.parseInt(finalTimeSplit[0]) + ":" + Integer.parseInt(finalTimeSplit[1]), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }).setNegativeButton("Cancel", null);
                            timePickerDialog.show();


                        }
                        else{
                            Toast.makeText(getActivity(), "Please set start time", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            }



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
