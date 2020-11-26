package com.example.stomanage.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseBaseModel {

    protected DatabaseReference DBRef;
    protected FirebaseAuth Auth;

    public FirebaseBaseModel(){
        Auth = FirebaseAuth.getInstance();
        DBRef = FirebaseDatabase.getInstance().getReference();
    }
}
