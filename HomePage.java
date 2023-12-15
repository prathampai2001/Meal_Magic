package com.example.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    Button btnCart,btnOrdered,btnAll,btnShowPizza,btnShowBurger,btnDrinks;
    Button btnlogout;
    TextView txtView;
        ProductDatabaseHandler db=new ProductDatabaseHandler(this);
        SharedPreferences sharedPreferences;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //initializing buttons
        btnCart=findViewById(R.id.btncartHome);
        btnAll=findViewById(R.id.buttonall);
        btnShowPizza=findViewById(R.id.buttonpizza);
        btnShowBurger=findViewById(R.id.buttonburger);
        btnOrdered=findViewById(R.id.btnordered);
            btnDrinks=findViewById(R.id.buttondrinks);
            txtView=findViewById(R.id.textView15);
            btnlogout=findViewById(R.id.btnlogout);

        renderFood("");//display's all type of food


            //when pizza button is pressed

            handelButtons(btnAll,"");
            handelButtons(btnShowPizza,"pizza");
            handelButtons(btnShowBurger,"burger");
            handelButtons(btnDrinks,"drink");

            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cartIntent=new Intent(HomePage.this, Cart.class);
                    startActivity(cartIntent);

                }
            });

            btnOrdered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent orderedPage=new Intent(HomePage.this,Ordered_page.class);
                    startActivity(orderedPage);
                }
            });
        }


private void handelButtons(Button btn,String food_type){
            btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SuspiciousIndentation")
                @Override
                public void onClick(View view) {
                    renderFood(food_type);
                    if(food_type.equals("")) {
                        txtView.setText("\t\t\tAll");
                    } else
                    txtView.setText("\t\t\t" + food_type);
                }
            });
}

    private void renderFood(String food_type) {
        //Loading food image and displaying in homepage dynamically
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parentLayout = findViewById(R.id.parentLinear);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        ArrayList<Product> products = food_type.equals("")?db.getAllFoodData():db.getAllFoodData(food_type);
        parentLayout.removeAllViews();
        for (Product p : products) {
            int resid = p.getRestaurant_id();
            String resname = p.getRestaurant_name();
            String resaddr = p.getRestaurant_address();
            String foodname = p.getFood_name();
            String foodrating = p.getFood_rating();
            String foodprice = p.getFood_price();
            String image_id = p.getImage_id();


            View relativeCard = inflater.inflate(R.layout.product_card_home_page, null);
            ImageView image = relativeCard.findViewById(R.id.imageFood);
            TextView txtFoodName = relativeCard.findViewById(R.id.txtfoodname);
            TextView txtFoodPrice = relativeCard.findViewById(R.id.txtfoodPrice);
            RelativeLayout layout = relativeCard.findViewById(R.id.itemCard);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),ProductPage.class);
                        intent.putExtra("restName", p.getRestaurant_name());
                    intent.putExtra("productName", p.getFood_name());
                    intent.putExtra("productPrice", p.getFood_price());
                    intent.putExtra("productImageId", p.getImage_id());
                    intent.putExtra("productFoodRating", p.getFood_rating());
                    startActivity(intent);
                }
            });

            int imageResourceId = getResources().getIdentifier(image_id, "drawable", getPackageName());
            image.setImageResource(imageResourceId);
            txtFoodName.setText(foodname);
            txtFoodPrice.setText(foodprice);
            parentLayout.addView(relativeCard);
        }

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent loginPage=new Intent(HomePage.this,MainActivity.class);
                startActivity(loginPage);
            }
        });
    }





}
