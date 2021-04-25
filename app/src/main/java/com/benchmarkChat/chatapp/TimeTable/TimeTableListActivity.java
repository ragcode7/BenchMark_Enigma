package com.benchmarkChat.chatapp.TimeTable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.benchmarkChat.chatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTableListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_list);

        ListView listView = findViewById(R.id.list_item);


        ArrayList<String> item = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();
        ArrayList<String> key = new ArrayList<>();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TimeTableListActivity.this, MainActivity.class);
                intent.putExtra("TYPE", 1);
                intent.putExtra("NAME", item.get(position));
                intent.putExtra("COUNT", countList.get(position));
                intent.putExtra("KEY", key.get(position));

                startActivity(intent);

            }
        });

        final int[] count = {-1};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.time_table_item, R.id.name, item);
        listView.setAdapter(arrayAdapter);

        FirebaseDatabase.getInstance().getReference().child("TimeTable")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            count[0]++;
                            item.add(count[0], dataSnapshot.child("NAME").getValue().toString());
                            countList.add(count[0], Integer.parseInt(dataSnapshot.child("COUNT").getValue().toString()));
                            key.add(count[0], dataSnapshot.getKey());
                            arrayAdapter.notifyDataSetChanged();

                            System.out.println(dataSnapshot.child("NAME").getValue());
                            System.out.println(dataSnapshot.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void infoActivity(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

}