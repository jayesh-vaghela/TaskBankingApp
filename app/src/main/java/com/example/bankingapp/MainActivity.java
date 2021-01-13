package com.example.bankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankingapp.JavaClasses.CustomAdapter;
import com.example.bankingapp.JavaClasses.DBHelper;
import com.example.bankingapp.JavaClasses.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    //private  boolean[] result;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ArrayList<String> userNames,userEmails,userBalance;
    private CustomAdapter adapter;
    private FloatingActionButton AddUser;
    private EditText uName,uEmail,uBalance;
    private Button Add,Cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        AddUser=findViewById(R.id.AddUser);
        dbHelper=new DBHelper(this);
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        userNames=new ArrayList<>(10);
        userEmails=new ArrayList<>(10);
        userBalance=new ArrayList<>(10);
        storeDataInArray();
        adapter=new CustomAdapter(this,userNames,userEmails,userBalance);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_LONG).show();
                TextView textView=new TextView(getApplicationContext());
                textView.setText("Add New user");
                textView.setPadding(20,30,20,30);
                textView.setTextSize(20F);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.parseColor("#0416B8"));
                textView.setTextColor(Color.WHITE);
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_layout1,null);
                builder.setView(view)
                        .setCancelable(false)
                        .setCustomTitle(textView)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                               builder.create().dismiss();
                            }
                        })
                        .setPositiveButton("Add User", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=uName.getText().toString();
                                String email=uEmail.getText().toString();
                                String balance=uBalance.getText().toString();
                                if(name.isEmpty() || email.isEmpty() || balance.isEmpty() || name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || balance.equalsIgnoreCase("")){
                                    Toast.makeText(getApplicationContext(),"Invalid Inputs",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    boolean res=dbHelper.insertUserData(name,email,balance);
                                    if(res){
                                        Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_LONG).show();
                                        recreate();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Something went Wrong!!!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                uName=view.findViewById(R.id.uName);
                uEmail=view.findViewById(R.id.uEmail);
                uBalance=view.findViewById(R.id.uBalance);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                builder.create().show();

            }
        });
    }

    private void storeDataInArray() {
        Cursor cursor=dbHelper.getData();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No Data Exist.",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
                userNames.add(cursor.getString(1));
                userEmails.add(cursor.getString(2));
                userBalance.add(cursor.getString(3));
            }
        }
    }
}