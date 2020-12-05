package com.example.stomanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.example.stomanage.firebase.model.FirebaseDBUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText _Email, _password;
    Button _logInButton;
    FirebaseAuth _auth;
    ProgressBar _prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _Email = findViewById(R.id.editTextLname);
        _password = findViewById(R.id.editTextTextPassword);
        _logInButton = findViewById(R.id.LogInButton);
        _auth = FirebaseAuth.getInstance();
        _prog = findViewById(R.id.LogInProgressBar);
        _prog.setVisibility(View.INVISIBLE);
        _logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  _Email.getText().toString().trim();
                String password = _password.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    _Email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    _password.setError("Password is required.");
                    return;
                }
                _logInButton.setVisibility(View.INVISIBLE);
                _prog.setVisibility(View.VISIBLE);
                _auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();
                            DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
                            DBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    GetUser(snapshot, uid, email);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            _password.setText("");
                            _prog.setVisibility(View.INVISIBLE);
                            _logInButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void GetUser(DataSnapshot snapshot, String uid, String email){
        UserObj user =  snapshot.child("users").child(uid).getValue(UserObj.class);
        _Email.setText("");
        _password.setText("");
        _prog.setVisibility(View.INVISIBLE);
        _logInButton.setVisibility(View.VISIBLE);
        openNextView(user, email);
    }

    private void openNextView(UserObj user,String email){
        Intent intent = null;
        if (user.getUserPerm().equals(Permissions.Perm.User.toString())) intent = new Intent(getApplicationContext(),UserMainScreen.class);//TODO UserMainScreen
        else if (user.getUserPerm().equals( Permissions.Perm.Manager.toString())) intent = new Intent(this,ManagerMainScreen.class);
        intent.putExtra("user", (Serializable)user);
        startActivity(intent);
    }
}