package com.benchmarkChat.chatapp.TimeTable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.benchmarkChat.chatapp.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void create(View view) {
        EditText tableName = findViewById(R.id.table_name);
        EditText tableCount = findViewById(R.id.table_count);

        String tableNameString = tableName.getText().toString(), tableCountString = tableCount.getText().toString();


        if(!tableNameString.isEmpty() && !tableCountString.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("NAME", tableNameString);
            intent.putExtra("COUNT", Integer.parseInt(tableCountString));
            intent.putExtra("TYPE", 0);
            startActivity(intent);
            finish();
        }
    }

}