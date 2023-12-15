package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class OrderSuccessPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success_page);
        Button btncmtorders=findViewById(R.id.btnmyorder);
        TextView txtconitue=findViewById(R.id.txtcontinueshopping);
        ImageView imageBack=findViewById(R.id.imageback);
        btncmtorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderedpage=new Intent(OrderSuccessPage.this, Ordered_page.class);
                startActivity(orderedpage);
            }
        });


        txtconitue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homepage=new Intent(OrderSuccessPage.this,HomePage.class);
                startActivity(homepage);
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}