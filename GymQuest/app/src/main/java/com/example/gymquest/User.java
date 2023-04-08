package com.example.alejandro_luna;

public class User {
    public String name,height,kcal,email,password,kg;


    public User(){

    }
    public User(String name, String height, String kg, String kcal, String email, String password){
        this.name=name;
        this.kg=kg;
        this.kcal=kcal;
        this.height=height;
        this.email=email;
        this.password=password;
    }
}
