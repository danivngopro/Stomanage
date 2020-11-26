package com.example.stomanage.firebase.model;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.stomanage.firebase.FirebaseBaseModel;
import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDBUser extends FirebaseBaseModel {


    public void addNewUserToDB(UserObj user, String password, Context context){
        Auth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    writeNewUserToDB(task.getResult().getUser().getUid(),user);
                }
                else{
                    Toast.makeText(context,"Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void writeNewUserToDB (String Uid, UserObj user){
        DBRef.child("users").child(Uid).setValue(user);

    }

    public DatabaseReference getUserFromDB(String uid){
        return DBRef.child("users").child(uid);
    }

}

