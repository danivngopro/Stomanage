package com.example.stomanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class UserMainScreen extends AppCompatActivity {

    TextView _name;
    Button oerderItemsButton, watchPersonalListsButton, orderFactoryItemsButton,watchOrderFactory, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");

        _name = (TextView)findViewById(R.id.NameText);
        _name.setText("שלום " + user.getFname());
        oerderItemsButton = (Button)findViewById(R.id.orderProductsForActivity);
        watchPersonalListsButton = (Button)findViewById(R.id.watchLists);
        orderFactoryItemsButton = (Button)findViewById(R.id.orderProductsForFactory);
        watchOrderFactory = (Button)findViewById(R.id.watchOrderFactory);
        logout = (Button)findViewById(R.id.logOutButton);



        oerderItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), itemList.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        watchPersonalListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), viewPersonallists.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        watchOrderFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), viewOpenFactories.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        orderFactoryItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), viewOpenFactoriesForOrders.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }


}