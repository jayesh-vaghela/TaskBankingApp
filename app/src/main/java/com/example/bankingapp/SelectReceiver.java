package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bankingapp.JavaClasses.CustomAdapter;
import com.example.bankingapp.JavaClasses.DBHelper;
import com.example.bankingapp.JavaClasses.UserAdapter;

import java.util.ArrayList;

public class SelectReceiver extends AppCompatActivity {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ArrayList<String> userNames, userEmails;
    private UserAdapter adapter;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_receiver);
        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        userNames = new ArrayList<>(10);
        userEmails = new ArrayList<>(10);
        storeDataInArray();
        adapter = new UserAdapter(this,userNames, userEmails);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectReceiver.this));
    }

    private void storeDataInArray() {
        Cursor cursor = dbHelper.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data Exist.", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                if(cursor.getString(1).equalsIgnoreCase(username)) {
                }
                else {
                    userNames.add(cursor.getString(1));
                    userEmails.add(cursor.getString(2));
                }
            }
        }
    }
}