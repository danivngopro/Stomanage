package com.example.stomanage.firebase.dataObject;


import com.example.stomanage.Permissions;

import java.io.Serializable;

public class UserObj implements Serializable {
    private String fname;
    private String lname;
    private String id;
    private String troop;
    private String email;
    private String phone;
    private String userPerm;

    public UserObj(){}

    public UserObj(String fname, String lname, String id, String troop, String email, String phone, String userPerm){
        this.fname = fname;
        this.lname = lname;
        this.id = id;
        this.troop = troop;
        this.email = email;
        this.phone = phone;
        this.userPerm  = userPerm;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTroop() {
        return troop;
    }

    public void setTroop(String troop) {
        this.troop = troop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPerm() {
        return userPerm;
    }

    public void setUserPerm(String userPerm) {
        this.userPerm = userPerm;
    }
}
