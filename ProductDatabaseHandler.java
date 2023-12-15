package com.example.fooddeliveryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodAppDB2";
    private static final String TABLE_NAME = "product";
    private static final String KEY_REST_ID = "rest_id"; //RESTURANT ID
    private static final String KEY_REST_NAME= "rest_name";
    private static final String KEY_REST_ADDRESS= "rest_addr";
    private static final String KEY_FOOD_TYPE = "food_type";
    private static final String KEY_FOOD_NAME= "food_name";
    private static final String KEY_FOOD_RATING= "food_rating";
    private static final String KEY_FOOD_PRICE= "food_price";
    private static final String KEY_FOOD_IMAGE_ID= "food_image_id";


    public ProductDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +KEY_REST_ID +" INTEGER ,"+
                KEY_REST_NAME +" TEXT,"+ KEY_REST_ADDRESS+" TEXT,"+KEY_FOOD_TYPE+" TEXT,"+KEY_FOOD_NAME +" TEXT,"+ KEY_FOOD_RATING+" TEXT,"+KEY_FOOD_PRICE+" TEXT,"+KEY_FOOD_IMAGE_ID+" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void insertFoodData(Product product){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_REST_ID,product.getRestaurant_id());
        values.put(KEY_REST_NAME,product.getRestaurant_name());
        values.put(KEY_REST_ADDRESS,product.getRestaurant_address());
        values.put(KEY_FOOD_TYPE,product.getFood_type());
        values.put(KEY_FOOD_NAME,product.getFood_name());
        values.put(KEY_FOOD_RATING,product.getFood_rating());
        values.put(KEY_FOOD_PRICE,product.getFood_price());
        values.put(KEY_FOOD_IMAGE_ID,product.getImage_id());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    //changed to new format
    ArrayList<Product> getAllFoodData() {
        ArrayList<Product> productList = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(KEY_REST_ID);
                int nameIndex = cursor.getColumnIndex(KEY_REST_NAME);
                int locationIndex = cursor.getColumnIndex(KEY_REST_ADDRESS);
                int foodTypeIndex = cursor.getColumnIndex(KEY_FOOD_TYPE);
                int foodNameIndex = cursor.getColumnIndex(KEY_FOOD_NAME);
                int ratingIndex = cursor.getColumnIndex(KEY_FOOD_RATING);
                int priceIndex = cursor.getColumnIndex(KEY_FOOD_PRICE);
                int imageIdIndex = cursor.getColumnIndex(KEY_FOOD_IMAGE_ID);

                do {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(locationIndex),
                            cursor.getString(foodTypeIndex),
                            cursor.getString(foodNameIndex),
                            cursor.getString(ratingIndex),
                            cursor.getString(priceIndex),
                            cursor.getString(imageIdIndex)
                    );
                    productList.add(product);
                } while (cursor.moveToNext());
            }
        }

        return productList;
    }



    ArrayList<Product> getAllFoodData(String food_type) {
        ArrayList<Product> productList = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+KEY_FOOD_TYPE+"=?", new String[]{food_type})) {
            int idIndex = cursor.getColumnIndex(KEY_REST_ID);
            int nameIndex = cursor.getColumnIndex(KEY_REST_NAME);
            int locationIndex = cursor.getColumnIndex(KEY_REST_ADDRESS);
            int foodNameType = cursor.getColumnIndex(KEY_FOOD_TYPE);
            int foodNameIndex = cursor.getColumnIndex(KEY_FOOD_NAME);
            int ratingIndex = cursor.getColumnIndex(KEY_FOOD_RATING);
            int priceIndex = cursor.getColumnIndex(KEY_FOOD_PRICE);
            int image_id = cursor.getColumnIndex(KEY_FOOD_IMAGE_ID);

            while (cursor.moveToNext()) {
                Product product = new Product(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(locationIndex),
                        cursor.getString(foodNameType),
                        cursor.getString(foodNameIndex),
                        cursor.getString(ratingIndex),
                        cursor.getString(priceIndex),
                        cursor.getString(image_id)
                );
                productList.add(product);
            }
        }

        return productList;
    }

    int getProductId(String restaurant_name,String foodName){
        SQLiteDatabase db = getReadableDatabase();
        int productId = -1; // Default value if user is not found

        String query = "SELECT " + KEY_REST_ID + " FROM " + TABLE_NAME + " WHERE " + KEY_REST_NAME + "=? AND " + KEY_FOOD_NAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{restaurant_name, foodName});

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_REST_ID);
            if (idIndex != -1) {
                productId = cursor.getInt(idIndex);
            }
        }

        cursor.close();
        return productId;
    }





}
