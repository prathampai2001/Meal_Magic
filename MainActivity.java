package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    ProductDatabaseHandler productdb=new ProductDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_juice_bottel);
//        setContentView(R.layout.activity_home_page);
//        setContentView(R.layout.activity_cart);
//        setContentView(R.layout.activity_product_page);
//        setContentView(R.layout.activity_registration);


        Button startorderbtn= findViewById(R.id.btnStartOrder);
        startorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("StatusPref",MODE_PRIVATE);
                sharedPreferences=getSharedPreferences("StatusPref",MODE_PRIVATE);
                boolean isDataLoaded=sharedPreferences.getBoolean("isDataLoaded",false);

                if(isDataLoaded){
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    productdb.insertFoodData(new Product("Subham", "manipal","burger", "Cheesburger", "4.5", "120", "burger"));
                    productdb.insertFoodData(new Product("7bees", "manipal", "pizza","Cheess pizza", "4.5", "120", "pizza"));
                    productdb.insertFoodData(new Product("Burger King", "Manhattan", "burger", "Whopper", "4.8", "150", "whopper"));
                    productdb.insertFoodData(new Product("McDonald's", "New York", "burger", "Big Mac", "4.7", "130", "big_mac"));
                    productdb.insertFoodData(new Product("Subway", "Chicago", "burger", "Chicken Teriyaki Sub", "4.5", "110", "chicken_teriyaki_sub"));
                    productdb.insertFoodData(new Product("Pizza Hut", "Los Angeles", "pizza", "Pepperoni Pizza", "4.6", "160", "pepperoni_pizza"));
                    productdb.insertFoodData(new Product("Domino's", "San Francisco", "pizza", "Margherita Pizza", "4.5", "140", "margherita_pizza"));
                    productdb.insertFoodData(new Product("Shake Shack", "Las Vegas", "burger", "SmokeShack", "4.9", "170", "smoke_shack"));
                    productdb.insertFoodData(new Product("Papa John's", "Seattle", "pizza", "Hawaiian Pizza", "4.4", "150", "hawaiian_pizza"));
                    productdb.insertFoodData(new Product("Five Guys", "Austin", "burger", "Bacon Cheeseburger", "4.7", "160", "bacon_cheess_burger"));
                    productdb.insertFoodData(new Product("Little Caesars", "Miami", "pizza", "Veggie Pizza", "4.6", "140", "veggie_pizza"));
                    productdb.insertFoodData(new Product("In-N-Out Burger", "San Diego", "burger", "Double-Double", "4.8", "180", "double_double_chess_burger"));
                    productdb.insertFoodData(new Product("Coca-Cola", "Various Locations", "drink", "Coca-Cola", "4.0", "2.5", "cola"));
                    productdb.insertFoodData(new Product("Pepsi", "Various Locations", "drink", "Pepsi", "4.0", "2.5", "pepsi"));
                    productdb.insertFoodData(new Product("Iced Tea", "Various Locations", "drink", "Iced Tea", "4.2", "3.0", "iced_tea"));


                    editor.putBoolean("isDataLoaded", true);
                    editor.apply();

                }
//                    Toast.makeText(MainActivity.this, "Dummy data added..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(i);

            }
        });

    }
}