package com.example.fooddeliveryapp;

public class User {
    int _id;
    private String _name,_email,_password,_phone_number,_address;

    public User(int id,String name,String email,String password,String phone,String address ){
        this._id=id;
        this._name=name;
        this._email=email;
        this._password=password;
        this._phone_number=phone;
        this._address=address;
    }


    public User(String name,String email,String password,String phone,String address ){
        this._name=name;
        this._email=email;
        this._password=password;
        this._phone_number=phone;
        this._address=address;
    }

    public int getID(){ return this._id; }
    public void setID(int id){ this._id = id; }
    public String getName() { return this._name; }
    public  void setName(String name) { this._name = name; }
    public String getEmail(){ return this._email; }
    public void setEmail(String email){ this._email = email; }
    public String getPass(){ return this._password; }
    public void setPass(String pass){ this._password = pass; }
    public  String getPhoneNumber() { return  this._phone_number; }
    public void setPhoneNumber(String phone_number) {this._phone_number = phone_number; }

    public String getAddress(){ return this._address; }

}

