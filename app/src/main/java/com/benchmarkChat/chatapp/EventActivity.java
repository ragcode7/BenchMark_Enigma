package com.benchmark.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.*;

import com.benchmarkChat.chatapp.R;

public class EventActivity extends AppCompatActivity {

    EditText title;
    EditText location;
    EditText description;
    Button addEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        title=findViewById(R.id.etTitle);
        location=findViewById(R.id.etLocation);
        description=findViewById(R.id.etDescription);
        addEvent=findViewById(R.id.AddEvent);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().isEmpty()&&!location.getText().toString().isEmpty()&&
                        !description.getText().toString().isEmpty())
                {
                    Intent intent=new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    intent.putExtra(Intent.EXTRA_EMAIL, "mahimalolla@gmail.com, accelbiaaccord@gmail.com");

                    if (intent.resolveActivity(getPackageManager()) != null)
                    {
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(EventActivity.this, "Please install a scheduling app or a Google Calendar to support this feature", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(EventActivity.this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}