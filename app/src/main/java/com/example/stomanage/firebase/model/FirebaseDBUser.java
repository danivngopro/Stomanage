package com.example.stomanage.firebase.model;

import com.example.stomanage.firebase.FirebaseBaseModel;
import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.firebase.database.DatabaseReference;

public class FirebaseDBUser extends FirebaseBaseModel {

    public void addUserToDB(String Uid, UserObj user){
        writeNewUser(Uid, user);
    }

    private void writeNewUser (String Uid, UserObj user){
        myRef.child("users").child(Uid).setValue(user);

    }

    public DatabaseReference getYserFromDB (String uid){
        return myRef.getRef().child("users").child(uid);
    }
}

