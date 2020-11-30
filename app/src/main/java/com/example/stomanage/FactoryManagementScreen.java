package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                startActivity(new Intent(getApplicationContext(),NewFactory.class));
                finish();
            }
        });
    }
}