package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.example.stomanage.firebase.model.FirebaseDBUser;

public class register extends AppCompatActivity {
    Spinner perms,troop;
    Button  SignUp;
    EditText fname, lname, id, email, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        perms = (Spinner)findViewById(R.id.spinnerPerms);
        troop = (Spinner)findViewById(R.id.spinnerTroop);
        SignUp = (Button)findViewById(R.id.buttonSignUp);
        fname = (EditText)findViewById(R.id.editTextfname);
        lname = (EditText)findViewById(R.id.editTextlname);
        id = (EditText)findViewById(R.id.editTextid);
        email = (EditText)findViewById(R.id.editTextemail);
        phone = (EditText)findViewById(R.id.editTextPhone);
        password = (EditText)findViewById(R.id.editTextTextPassword);

        Permissions.Perm[] Perms = Permissions.Perm.values();
        String items[] = new String [Perms.length];
        for (int i = 0; i < Perms.length; i++){
            items[i] = Perms[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        perms.setAdapter(adapter);


        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserObj user = new UserObj(fname.getText().toString().trim(),
                        lname.getText().toString().trim(),
                        id.getText().toString().trim(),
                        "יובל"//troop.getSelectedItem().toString(),
                        ,email.getText().toString().trim(),
                        phone.getText().toString().trim(),
                       perms.getSelectedItem().toString());
                FirebaseDBUser DBU = new FirebaseDBUser();
                DBU.addNewUserToDB(user,  password.getText().toString().trim(), getApplicationContext());
                finish();
            }
        });







    }
}