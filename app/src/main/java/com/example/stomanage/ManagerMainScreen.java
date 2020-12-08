package com.example.stomanage;

import android.content.Intent;
import android.os.Bundle;

import com.example.stomanage.firebase.FirebaseBaseModel;
import com.example.stomanage.firebase.dataObject.UserObj;
import com.example.stomanage.firebase.model.FirebaseDBUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class ManagerMainScreen extends AppCompatActivity {

    TextView _name;
    Button _Troop,_User,_Factory,_Warehouse,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main_screen);

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");

        _name = (TextView)findViewById(R.id.NameText);
        _name.setText("שלום " + user.getFname());
        _Troop = (Button)findViewById(R.id.ButtonTroop);
        _User = (Button)findViewById(R.id.buttonUser);
        _Factory = (Button)findViewById(R.id.buttonFactory);
        _Warehouse = (Button)findViewById(R.id.buttonWarehouse);
        logout = (Button)findViewById(R.id.logOutButton);

        _User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        _Troop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TroopManagerScreen.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        _Factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FactoryManagementScreen.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        _Warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WarehouseManagementScreen.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ManagerMainScreen.this, Login.class);
                startActivity(intent);
            }
        });

    }
}