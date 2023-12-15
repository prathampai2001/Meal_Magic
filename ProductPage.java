package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductPage extends AppCompatActivity {

    Button btnAddtoCart,btnincrement,btndecrement;
    TextView txtqty;
    String restaurantName,productName,productPrice,productImageId,foodRating;
    CartDatabaseHandler cartDatabaseHandler=new CartDatabaseHandler(this);
    UserDatabaseHandler userDatabaseHandler=new UserDatabaseHandler(this);
    ProductDatabaseHandler productDatabaseHandler=new ProductDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        ImageButton backBtn = findViewById(R.id.bckButton);
        btnAddtoCart=findViewById(R.id.btnaddtocart);
        txtqty=findViewById(R.id.txtqty_product);
        btndecrement=findViewById(R.id.btndecrement);
        btnincrement=findViewById(R.id.btnincrement);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
             restaurantName = intent.getStringExtra("restName");
             productName = intent.getStringExtra("productName");
             productPrice = intent.getStringExtra("productPrice");
             productImageId = intent.getStringExtra("productImageId");
             foodRating = intent.getStringExtra("productFoodRating");


            // Find your TextView after setContentView
            TextView txtProductName = findViewById(R.id.txtfoodnameProd);
            TextView txtFoodprice = findViewById(R.id.txtfoodPriceProd);
            ImageView imgView = findViewById(R.id.txtimageProd);

            txtProductName.setText(productName);
            txtFoodprice.setText(productPrice);
//            imgView.setBackgroundResource(Integer.parseInt(foodRating));
            imgView.setImageResource(getResources().getIdentifier(productImageId, "drawable", getPackageName()));
        }

        btnincrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty= Integer.parseInt(txtqty.getText().toString());
                qty++;

                txtqty.setText(String.valueOf(qty));
            }
        });

        btndecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty= Integer.parseInt(txtqty.getText().toString());
                qty--;

                txtqty.setText(String.valueOf(qty));
            }
        });

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
                String user_email=sharedPreferences.getString("useremail","");
                String user_password=sharedPreferences.getString("password","");

                int userid=userDatabaseHandler.getUserId(user_email,user_password);
                int productid=productDatabaseHandler.getProductId(restaurantName,productName);
                int tempqty= Integer.parseInt(txtqty.getText().toString());
                cartDatabaseHandler.addItemToCart(userid,productid,tempqty,productPrice,productName,productImageId);

                finish();
            }
        });
    }
}
