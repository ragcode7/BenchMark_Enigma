package com.benchmarkChat.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.benchmarkChat.chatapp.Fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

                getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new ProfileFragment())
                .commit();

    }

}