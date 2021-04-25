package com.benchmarkChat.chatapp.TimeTable;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.benchmarkChat.chatapp.R;

public class SettingsDialogFragment extends DialogFragment {

    String startTime;
    int dataHolder;
    Adapter adapter;

    public SettingsDialogFragment(String startTime, int dataHolder, Adapter adapter) {
        this.startTime = startTime;
        this.dataHolder = dataHolder;
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_customiser_layout,container, false);

        SettingsFragment settingsFragment = new SettingsFragment(startTime, dataHolder, adapter, this);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction
                .replace(R.id.frame_layout, settingsFragment)
                .commit();

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return view;
    }

}
