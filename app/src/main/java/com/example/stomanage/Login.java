package com.example.stomanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText _Email, _password;
    Button _logInButton;
    FirebaseAuth _auth;
    ProgressBar _prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _Email = findViewById(R.id.editTextTextEmailAddress);
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
                            startActivity(new Intent(getApplicationContext(), ManagerMainScreen.class));
                            _Email.setText("");


                        }
                        else{
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                        _password.setText("");
                        _prog.setVisibility(View.INVISIBLE);
                        _logInButton.setVisibility(View.VISIBLE);


                    }
                });
            }
        });


    }
}