package com.example.fooddeliveryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodAppDB";
     static final String TABLE_NAME = "registration";
     static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_ADDRESS = "address";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +KEY_ID +" INTEGER PRIMARY KEY,"+ KEY_NAME +" TEXT,"+ KEY_PASS+" TEXT,"+KEY_EMAIL +" TEXT,"+KEY_PH_NO +" TEXT,"+ KEY_ADDRESS+" TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addUser(User user){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        Log.d("testing",user.getName());
        values.put(KEY_NAME,user.getName());
        values.put(KEY_PASS,user.getPass());
        values.put(KEY_EMAIL,user.getEmail());
        values.put(KEY_PH_NO,user.getPhoneNumber());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    void updateUserPassword(String phno,String newpass){
        SQLiteDatabase db=getReadableDatabase();
//        String query="UPDATE "+TABLE_NAME+" SET "+KEY_PASS+"=? WHERE "+KEY_PH_NO+"=?";
//
//        Cursor cursor=db.rawQuery(query,new String[]{newpass,phno});

        ContentValues values = new ContentValues();
        values.put(KEY_PASS, newpass);
        String whereClause = KEY_PH_NO + " = ?";
        String[] whereArgs = {phno};
        db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    boolean findUser(String name,String password){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_EMAIL+"=? AND "+KEY_PASS+"=?";
        Cursor cursor=db.rawQuery(query,new String[]{name,password});
        boolean result=cursor.moveToFirst();
        cursor.close();
        return result;

    }

    public int getUserId(String name, String password) {
        SQLiteDatabase db = getReadableDatabase();
        int userId = -1; // Default value if user is not found

        String query = "SELECT " + KEY_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL + "=? AND " + KEY_PASS + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{name, password});

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            if (idIndex != -1) {
                userId = cursor.getInt(idIndex);
            }
        }

        cursor.close();
        return userId;
    }

    boolean findUserPhno(String phno){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_PH_NO+"=?";
        Cursor cursor=db.rawQuery(query,new String[]{phno});
        boolean result=cursor.moveToFirst();
        cursor.close();
        return result;

    }
}
