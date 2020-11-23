package com.example.stomanage.firebase.dataObject;

enum Permissions {Manager, User}

public class UserObj {
    public String fname;
    public String lname;
    public String troop;
    public String email;
    public String phone;
    public Permissions perm;

    public UserObj(String fname, String lname, String troop, String email, String phone, Permissions perm){
        this.fname = fname;
        this.lname = lname;
        this.troop = troop;
        this.email = email;
        this.phone = phone;
        this.perm  = perm;
    }
}
