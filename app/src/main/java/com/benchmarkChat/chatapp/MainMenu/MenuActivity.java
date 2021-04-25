package com.benchmarkChat.chatapp.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benchmarkChat.chatapp.MainActivity;
import com.benchmarkChat.chatapp.MapsActivity;
import com.benchmarkChat.chatapp.ProfileActivity;
import com.benchmarkChat.chatapp.R;
import com.benchmarkChat.chatapp.TimeTable.InfoActivity;
import com.benchmarkChat.chatapp.TimeTable.TimeTableListActivity;

public class MenuActivity extends AppCompatActivity
{
    ImageButton eventbtn;
    ImageButton friendsbtn;
    ImageButton doubtbtn;
    ImageButton cornerbtn;
    ImageButton timebtn;
    ImageButton profilebutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        eventbtn = (ImageButton)findViewById(R.id.evt_btn);
        friendsbtn = (ImageButton)findViewById(R.id.fyf_btn);
        doubtbtn = (ImageButton)findViewById(R.id.aad_btn);
        cornerbtn = (ImageButton)findViewById(R.id.sc_btn);
        timebtn = (ImageButton)findViewById(R.id.ttatdl_btn);
        profilebutton = findViewById(R.id.profile_redirect);

        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
            startActivity(intent);
            }
        });



        eventbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent loadEvents = new Intent(MenuActivity.this, com.benchmark.event.EventActivity.class);//Replace with Events class
                startActivity(loadEvents);
            }
        });
        friendsbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent loadFriends = new Intent(MenuActivity.this, MapsActivity.class);//Replace with class
                startActivity(loadFriends);
            }
        });
        doubtbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
              Intent loadDoubt = new Intent(MenuActivity.this, MainActivity.class);//Replace with class
                startActivity(loadDoubt);
            }
        });
        cornerbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
      //          Intent loadCorner = new Intent(MenuActivity.this, MainActivity.class);//Replace with class
      //          startActivity(loadCorner);
            }
        });
        timebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent loadTimeTable = new Intent(MenuActivity.this, TimeTableListActivity.class);//Replace with class
                startActivity(loadTimeTable);
            }
        });

    }
}
