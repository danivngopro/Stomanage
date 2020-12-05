package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FactoryManagementScreen extends AppCompatActivity {

    FloatingActionButton createNew, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_management_screen);

        createNew = findViewById(R.id.createNew);
        search = findViewById(R.id.search);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                dialog = new Dialog(FactoryManagementScreen.this);
                dialog.setContentView(R.layout.activity_new_factory);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.getWindow().getAttributes().windowAnimations = R.style.popUpAnimation;
                dialog.show();


                Button addProductButton = dialog.findViewById(R.id.addProductButton);
                addProductButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText FactoryName = (EditText)(dialog.findViewById(R.id.FactoryName));
                        String FactoryNameString = FactoryName.getText().toString();
                        if(FactoryNameString.trim().length() > 0) {
                            Toast.makeText(FactoryManagementScreen.this, "Factory Created", Toast.LENGTH_SHORT).show();
                            addValueToFirebase(FactoryNameString);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(FactoryManagementScreen.this, "please enter Factory name", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void addValueToFirebase(String FactoryName) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");
        String id = user.getId();
        mDatabase.child("Factories").child(FactoryName).child("items").setValue(" :");
    }
}