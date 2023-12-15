package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.SharedPreferences;

public class LoginPage extends AppCompatActivity {
EditText txtUserName,txtpass;
Button btnSubmit,btnRegister;
TextView txtforget;
SharedPreferences sharedPreferences;
UserDatabaseHandler db=new UserDatabaseHandler(this);
ProductDatabaseHandler productdb=new ProductDatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        txtUserName=findViewById(R.id.username);
        txtpass=findViewById(R.id.password);
        btnSubmit=findViewById(R.id.btnlogin);
        btnRegister=findViewById(R.id.register1);
        txtforget=findViewById(R.id.txtforget);
        sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
        String user_email=sharedPreferences.getString("useremail","");
        String user_password=sharedPreferences.getString("password","");

//        String user_email=txtUserName.getText().toString();
//        String user_password=txtpass.getText().toString();
        if(db.findUser(user_email,user_password)){

            Intent intent=new Intent(LoginPage.this, HomePage.class);
            startActivity(intent);
        }else {
            //Toast.makeText(LoginPage.this, "User not found", Toast.LENGTH_SHORT).show();

        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=txtUserName.getText().toString();
                String pass=txtpass.getText().toString();


                if(db.findUser(userName,pass)){

                    Intent intent=new Intent(LoginPage.this, HomePage.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginPage.this, "User not found", Toast.LENGTH_SHORT).show();

                }
            }
        });


    btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i =new Intent(LoginPage.this, Registration.class);
            startActivity(i);
        }
    });

 txtforget.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent i=new Intent(LoginPage.this, ForgetPassword.class);
         startActivity(i);
     }
 });


    }



}