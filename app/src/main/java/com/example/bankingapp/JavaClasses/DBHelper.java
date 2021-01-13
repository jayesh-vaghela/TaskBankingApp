package com.example.bankingapp.JavaClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"BankUserDetails.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table UserDetails(id INTEGER Primary key Autoincrement,name TEXT,email TEXT,balance TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table if exists UserDetails");
            onCreate(db);
    }
    public boolean insertUserData(String name,String email,String balance){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("balance",balance);
        long result= db.insert("UserDetails",null,contentValues);
        if(result==-1){
            return false;
        }
        return true;
    }
    public boolean updateUserData(String name,String balance){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("balance",balance);
        Cursor cursor=db.rawQuery("Select * from UserDetails where name=?",new String[] {name});
        if(cursor.getCount()>0) {
            long result = db.update("UserDetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            }
            return true;
        }
        return false;
    }

    public String getUserBalance(String receiver){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        String userBalance="";
        if(db != null){
            cursor=db.rawQuery("Select * from UserDetails where name=?",new String[] {receiver});
        }
        if(cursor.getCount()!=0 && cursor.moveToNext()){
            userBalance=cursor.getString(3);
        }
        return userBalance;
    }
    public Cursor getData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db != null){
            cursor=db.rawQuery("Select * from UserDetails",null);
        }
        return cursor;
    }
}
