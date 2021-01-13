package com.example.bankingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankingapp.JavaClasses.DBHelper;
import com.example.bankingapp.JavaClasses.Transaction;

public class UserProfile extends AppCompatActivity {
    private TextView userName,sender;
    private TextView userBalance,receiver,emailid;
    private EditText amount;
    private ImageView userImage;
    private Button sendMoney;
    private String senderName,balance,email;
    private DBHelper dbHelper;
    private Transaction transaction;
    private String receiverName="",rec="";
    private boolean result=false,res=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userName=findViewById(R.id.profileName);
        userBalance=findViewById(R.id.profileBalance);
        userImage=findViewById(R.id.profileImage);
        sendMoney=findViewById(R.id.sendMoney);
        emailid=findViewById(R.id.profileEmail);
        Intent intent=getIntent();
        balance=intent.getStringExtra("userbalance");
        senderName=intent.getStringExtra("username");
        email=intent.getStringExtra("useremail");
        emailid.setText("Email : "+intent.getStringExtra("useremail"));
        userBalance.setText("Balance : \u20B9"+intent.getStringExtra("userbalance"));
        userName.setText("UserName : "+intent.getStringExtra("username"));
        userImage.setImageResource(R.drawable.profile_icon);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(UserProfile.this,SelectReceiver.class);
                intent1.putExtra("username",intent.getStringExtra("username"));
                startActivityForResult(intent1,101);
            }
        });
        if(savedInstanceState!=null){
            senderName=savedInstanceState.getString("senderName");
            balance=savedInstanceState.getString("balance");
            email=savedInstanceState.getString("email");
            rec=savedInstanceState.getString("rec");
            res=savedInstanceState.getBoolean("res");
            emailid.setText("Email : "+email);
            userBalance.setText("Balance : \u20B9"+balance);
            userName.setText("UserName : "+senderName);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode==RESULT_OK){
               TextView textView=new TextView(getApplicationContext());
                textView.setText("Transaction Details");
                textView.setPadding(20,30,20,30);
                textView.setTextSize(20F);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.parseColor("#0416B8"));
                textView.setTextColor(Color.WHITE);
                receiverName=data.getStringExtra("receiver");
                result=true;
                AlertDialog.Builder builder=new AlertDialog.Builder(UserProfile.this);
                LayoutInflater inflater=this.getLayoutInflater();
                View view=inflater.inflate(R.layout.dialog_layout,null);
                builder.setView(view)
                        .setCancelable(false)
                        .setCustomTitle(textView)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                                builder.create().cancel();
                            }
                        })
                        .setPositiveButton("Send Money", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String transferMoney=amount.getText().toString();
                                if(Integer.valueOf(balance)>Integer.valueOf(transferMoney)){
                                    dbHelper=new DBHelper(UserProfile.this);
                                    transaction=new Transaction(UserProfile.this);
                                    String newSenderBalance=String.valueOf(Integer.valueOf(balance)-Integer.valueOf(transferMoney));
                                    boolean update=dbHelper.updateUserData(senderName,newSenderBalance);
                                    String receiverBalance=dbHelper.getUserBalance(receiverName);
                                    String newReceiverBalance=String.valueOf(Integer.valueOf(receiverBalance)+Integer.valueOf(transferMoney));
                                    if(receiverBalance.equalsIgnoreCase("")){
                                        Toast.makeText(getApplicationContext(),"Transfer Failed",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    boolean update1=dbHelper.updateUserData(receiverName,newReceiverBalance);
                                    if(update && update1){
                                        boolean transfer=transaction.insertTransaction(senderName,receiverName,balance,receiverBalance,newSenderBalance,newReceiverBalance,transferMoney);
                                        if(transfer){
                                            userBalance.setText("Balance : \u20B9"+newSenderBalance);
                                            Toast.makeText(getApplicationContext(),"Transfer SuccessFully",Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"Transfer Failed",Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Transfer Failed",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),"Transfer Money Exceeds Balance.",Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });
                sender=view.findViewById(R.id.sender);
                receiver=view.findViewById(R.id.receiver);
                amount=view.findViewById(R.id.amount);
                sender.setText("Sender : "+senderName);
                receiver.setText("Receiver : "+receiverName);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                builder.create().show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("senderName",senderName);
        outState.putString("balance",balance);
        outState.putString("email",email);
    }
}