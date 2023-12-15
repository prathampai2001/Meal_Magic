package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class Registration extends AppCompatActivity {

    EditText txtName,txtEmail,txtPassword,txtphone;
    String name,email,pass,phone;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtName=findViewById(R.id.username);
        txtEmail=findViewById(R.id.email);
        txtphone=findViewById(R.id.phone);
        txtPassword=findViewById(R.id.password);
        btnSubmit=findViewById(R.id.regibutton);
        UserDatabaseHandler db=new UserDatabaseHandler(this);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=txtName.getText().toString();
                email=txtEmail.getText().toString();
                pass=txtPassword.getText().toString();
                phone=txtphone.getText().toString();
                db.addUser(new User(name,email,pass,phone,""));

                SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("useremail",email);
                editor.putString("password",pass);
                editor.commit();

                String user_email=sharedPreferences.getString("useremail","");
                String user_password=sharedPreferences.getString("password","");


                Intent i =new Intent(Registration.this,LoginPage.class);
                startActivity(i);
            }
        });
    }
}