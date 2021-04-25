package com.benchmarkChat.chatapp.TimeTable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.benchmarkChat.chatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_layout);

        Intent intent = getIntent();
        int type = intent.getIntExtra("TYPE", 0);
        final int count = intent.getIntExtra("COUNT", 0);
        final String name = intent.getStringExtra("NAME");
        String key  = null;
        if(type == 1){
            key = intent.getStringExtra("KEY");
        }



        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7, GridLayoutManager.VERTICAL, false);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final DataHolder[] dataHolders = new DataHolder[count * 7];
        final Adapter adapter = new Adapter(this, count, getSupportFragmentManager(),dataHolders);

        if(type == 0) {
            findViewById(R.id.complete_table).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int check = 0;
                    for (int i = 0; i < dataHolders.length; i++) {
                        if (dataHolders[i] == null) {
                            check++;
                        }
                    }
                    if (check == 0) {
                        FirebaseDatabase.getInstance().getReference().child("TimeTable").push().setValue(0, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                String key = ref.getKey();
                                FirebaseDatabase.getInstance().getReference().child("TimeTable").child(key).setValue("");
                                FirebaseDatabase.getInstance().getReference().child("TimeTable").child(key).child("NAME").setValue(name);
                                FirebaseDatabase.getInstance().getReference().child("TimeTable").child(key).child("COUNT").setValue(count);
                                for (int i = 0; i < dataHolders.length; i++) {
                                    FirebaseDatabase.getInstance().getReference().child("TimeTable").child(key).child("DATA").push().setValue(dataHolders[i]);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            findViewById(R.id.complete_table).setVisibility(View.GONE);
            FirebaseDatabase.getInstance().getReference().child("TimeTable")
                    .child(key)
                    .child("DATA")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int i = -1;
                            for(DataSnapshot snap : snapshot.getChildren()){
                                i++;
                                dataHolders[i] = snap.getValue(DataHolder.class);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


    }
}