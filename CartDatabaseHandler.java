package com.example.fooddeliveryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class CartDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodAppDB_Cart";
    private static final String TABLE_NAME_CART = "cart";
    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_USER_ID = "user_id"; // Foreign key referencing users
    private static final String KEY_ITEM_ID = "item_id"; // Foreign key referencing items
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";
    private static final String KEY_ITEM_NAME = "itemName";
    private static final String KEY_ITEM_Image_ID = "imageid";

    public CartDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME_CART + "(" +
                KEY_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_ID + " INTEGER," +
                KEY_ITEM_ID + " INTEGER," +
                KEY_ITEM_NAME + " TEXT," +
                KEY_QUANTITY + " INTEGER," +
                KEY_PRICE + " REAL," +
                KEY_ITEM_Image_ID+" TEXT,"+
                "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + UserDatabaseHandler.TABLE_NAME + "(" + UserDatabaseHandler.KEY_ID + ")," +
                "FOREIGN KEY(" + KEY_ITEM_ID + ") REFERENCES your_item_table_name(your_item_id_column_name)" +
                ")";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CART);
        onCreate(db);
    }

    void addItemToCart(int userId, int itemId, int quantity, String price
            ,String itemName,String image_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, userId);
        values.put(KEY_ITEM_ID, itemId);
        values.put(KEY_ITEM_NAME, itemName);
        values.put(KEY_QUANTITY, quantity);
        values.put(KEY_PRICE, price);
        values.put(KEY_ITEM_Image_ID,image_id);
        db.insert(TABLE_NAME_CART, null, values);
        db.close();
    }
    public ArrayList<CartItems> getCartItemsByUserId(int userId) {
        ArrayList<CartItems> cartItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_NAME_CART, null, KEY_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null)) {

            int cartIdIndex = cursor.getColumnIndex(KEY_CART_ID);
            int itemIdIndex = cursor.getColumnIndex(KEY_ITEM_ID);
            int itemNameIndex = cursor.getColumnIndex(KEY_ITEM_NAME); // Assuming there is a column for item name
            int quantityIndex = cursor.getColumnIndex(KEY_QUANTITY);
            int priceIndex = cursor.getColumnIndex(KEY_PRICE);
            int image_id=cursor.getColumnIndex(KEY_ITEM_Image_ID);
            while (cursor.moveToNext()) {
                CartItems cartItem = new CartItems(
                        cursor.getInt(cartIdIndex),
                        userId,
                        cursor.getInt(itemIdIndex),
                        cursor.getString(itemNameIndex),
                        cursor.getInt(quantityIndex),
                        cursor.getDouble(priceIndex),
                        cursor.getString(image_id)
                );
                cartItemList.add(cartItem);
            }
        } finally {
            db.close();
        }

        return cartItemList;
    }
    public void cancelOrder(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rowsAffected = db.delete(TABLE_NAME_CART, KEY_CART_ID + " = ?", new String[]{String.valueOf(itemId)});
            Log.d("Rows deleted", String.valueOf(rowsAffected));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteAllcartItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
             db.delete(TABLE_NAME_CART, null,null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void deleteCartItemById(int cartItemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int rowsAffected = db.delete(TABLE_NAME_CART, KEY_CART_ID + " = ?", new String[]{String.valueOf(cartItemId)});
            Log.d("Rows deleted", String.valueOf(rowsAffected));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void incrementCarteqty(int cartItemId){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
        String selectQuery = "SELECT " + KEY_QUANTITY + " FROM " + TABLE_NAME_CART + " WHERE " + KEY_CART_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cartItemId)});

        if (cursor.moveToFirst()) {
            int currentQuantity = Integer.parseInt(cursor.getString(0));

            // Step 2: Increment the quantity
            int newQuantity = currentQuantity + 1;

            // Step 3: Update the incremented value in the database
            ContentValues values = new ContentValues();
            values.put(KEY_QUANTITY, newQuantity);

            String whereClause = KEY_CART_ID + " = ?";
            String[] whereArgs = {String.valueOf(cartItemId)};

            int rowsAffected = db.update(TABLE_NAME_CART, values, whereClause, whereArgs);
        }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
    }

    public void decrementCarteqty(int cartItemId){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            String selectQuery = "SELECT " + KEY_QUANTITY + " FROM " + TABLE_NAME_CART + " WHERE " + KEY_CART_ID + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cartItemId)});

            if (cursor.moveToFirst()) {
                int currentQuantity = Integer.parseInt(cursor.getString(0));

                // Step 2: Increment the quantity
                int newQuantity = currentQuantity - 1;

                // Step 3: Update the incremented value in the database
                ContentValues values = new ContentValues();
                values.put(KEY_QUANTITY, newQuantity);

                String whereClause = KEY_CART_ID + " = ?";
                String[] whereArgs = {String.valueOf(cartItemId)};

                int rowsAffected = db.update(TABLE_NAME_CART, values, whereClause, whereArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}
