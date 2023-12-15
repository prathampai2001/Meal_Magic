package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Ordered_page extends AppCompatActivity {
    private OrderedDatabaseHandler dbHelper;
    private UserDatabaseHandler db;
    Button btnContinueShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordered_page);
        LinearLayout itemLayout = findViewById(R.id.itemLayout);
        ImageButton backBtn = findViewById(R.id.imageBtn);
        btnContinueShopping=findViewById(R.id.btnContinueShopping);
        itemLayout.removeAllViews();
        // Use the correct layout file
//        ScrollView scrollView = findViewById(R.id.scrollView);
//        LinearLayout linearLayout = findViewById(R.id.itemLayout);
//        for (int i = 0; i < 4; i++) {
//            // Inflate the layout from XML
//            ConstraintLayout inflatedLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_cart_item, null);
//
//            // Customize the content of the inflated layout if needed
//
//            // Add the inflated layout to the LinearLayout
//            linearLayout.addView(inflatedLayout);
//        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHomePage=new Intent(Ordered_page.this, HomePage.class);
                startActivity(intentHomePage);
            }
        });
        dbHelper = new OrderedDatabaseHandler(this);
        db= new UserDatabaseHandler(this);

        // Example: Inserting an item



        // Example: Retrieving all items
        SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
        String user_email=sharedPreferences.getString("useremail","");
        String user_password=sharedPreferences.getString("password","");
        ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId( db.getUserId(user_email,user_password));

        for (CartItems cartItem : cartItemList) {

            addNewItemToLayout(cartItem);
        }

    }
    private void addNewItemToLayout(CartItems cartItems) {
        // Get your LinearLayout
        LinearLayout itemLayout = findViewById(R.id.itemLayout);


        // Inflate your item view (the RelativeLayout in your case)
        View itemView = getLayoutInflater().inflate(R.layout.product_card_ordered_page, null);

        // Update the TextViews and other UI elements in the item view with product information
        TextView txtFoodName = itemView.findViewById(R.id.txtfoodname);
        TextView amt = itemView.findViewById(R.id.amt);
        TextView location = itemView.findViewById(R.id.location);
        TextView qty = itemView.findViewById(R.id.qty);
        Button cancelBtn = itemView.findViewById(R.id.cancel_button);
        txtFoodName.setText(cartItems.getItemName());
        amt.setText( String.valueOf(cartItems.getPrice()));
        location.setText("location");
        qty.setText(String.valueOf(cartItems.getQuantity()));// You may need to update this based on your logic

        // Add the item view to your LinearLayout
        itemLayout.addView(itemView);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbHelper.cancelOrder(cartItems.getCartId());
                itemLayout.removeAllViews();
                SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
                String user_email=sharedPreferences.getString("useremail","");
                String user_password=sharedPreferences.getString("password","");
                ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId( db.getUserId(user_email, user_password));

                for (CartItems cartItem : cartItemList) {
                    Log.d("CartItem", cartItem.toString());
                    addNewItemToLayout(cartItem);
                }
            }
        });
    }


}

