package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;


public class ForgetPassword extends AppCompatActivity {
    EditText new_pass,phone,txt_otp_code ;
    Button btn_verify,btn_getotp,btn_reset;
    int random_opt;
    String phoneNumber;

    UserDatabaseHandler userDb=new UserDatabaseHandler(this);
  //  ForgetDatabaseHandler forgetDatabaseHandler=new ForgetDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        phone=findViewById(R.id.phone);
        btn_verify=findViewById(R.id.btnotp);
        btn_getotp=findViewById(R.id.get_otp);
        btn_reset=findViewById(R.id.reset);
        txt_otp_code=findViewById(R.id.otpcode);
        new_pass = findViewById(R.id.new_pass);

        new_pass.setVisibility(View.INVISIBLE);
        btn_verify.setVisibility(View.INVISIBLE);
        btn_reset.setVisibility(View.INVISIBLE);

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_code=txt_otp_code.getText().toString();
                int temp= Integer.parseInt(txt_code);

                if(temp==random_opt){
                    txt_otp_code.setVisibility(View.INVISIBLE);
                    btn_verify.setVisibility(View.INVISIBLE);
                    new_pass.setVisibility(View.VISIBLE);
                    btn_reset.setVisibility(view.VISIBLE);
                }
            }
        });
        btn_getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 phoneNumber = phone.getText().toString();
                //checking for user phone number in the DB
                if(userDb.findUserPhno(phoneNumber)){
                    btn_getotp.setVisibility(View.INVISIBLE);
                    btn_verify.setVisibility(View.VISIBLE);

                    //if the phone number exist
                    random_opt=generate4DigitNumber();
                    String message = "Hello, this is your OTP: "+random_opt;

                    Intent sentIntent = new Intent("SMS_SENT");
                    PendingIntent sentPI = PendingIntent.getBroadcast(ForgetPassword.this, 0, sentIntent, PendingIntent.FLAG_IMMUTABLE);

                    Intent deliveredIntent = new Intent("SMS_DELIVERED");
                    PendingIntent deliveredPI = PendingIntent.getBroadcast(ForgetPassword.this, 0, deliveredIntent, PendingIntent.FLAG_IMMUTABLE);

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
                }
                else{
                    Toast.makeText(ForgetPassword.this, "Invalid Phone number ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userDb.updateUserPassword(phoneNumber,new_pass.getText().toString());

                Toast.makeText(ForgetPassword.this, "Password updated succesfully", Toast.LENGTH_SHORT).show();
                Intent loginpage=new Intent(ForgetPassword.this,LoginPage.class);
                startActivity(loginpage);

            }
        });
    }
    public int generate4DigitNumber() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // Generates a random number between 1000 and 9999 (inclusive).
    }

}


