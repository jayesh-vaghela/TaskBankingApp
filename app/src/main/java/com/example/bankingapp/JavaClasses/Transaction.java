package com.example.bankingapp.JavaClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Transaction extends SQLiteOpenHelper {

    public Transaction(Context context) {
        super(context,"Transaction.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table UserTransactions(Sender TEXT,Receiver TEXT,SenderBalance TEXT,ReceiverBalance TEXT,SenderNewBalance TEXT,ReceiverNewBalance TEXT,AmountTransfer TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists UserTransactions");
        onCreate(db);
    }

    public boolean insertTransaction(String Sender,String Receiver,String SenderBalance,String ReceiverBalance,String SenderNewBalance,String ReceiverNewBalance,String AmountTransfer){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Sender",Sender);
        contentValues.put("Receiver",Receiver);
        contentValues.put("SenderBalance",SenderBalance);
        contentValues.put("ReceiverBalance",ReceiverBalance);
        contentValues.put("SenderNewBalance",SenderNewBalance);
        contentValues.put("ReceiverNewBalance",ReceiverNewBalance);
        contentValues.put("AmountTransfer",AmountTransfer);
        long result= db.insert("UserTransactions",null,contentValues);
        if(result==-1){
            return false;
        }
        return true;
    }
}
