package com.example.feespaymentapp;
public class User {
    public String email, username, password, s_id, year, branch;

    public User(){

    }

    public User(String email, String username, String password, String s_id, String year, String branch){
        this.email= email;
        this.username= username;
        this.password= password;
        this.s_id= s_id;
        this.year= year;
        this.branch= branch;
    }
}
