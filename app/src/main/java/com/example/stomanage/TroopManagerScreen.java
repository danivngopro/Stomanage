package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

public class TroopManagerScreen extends AppCompatActivity {

    FloatingActionButton createNew, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troop_manager_screen);

        createNew = (FloatingActionButton)findViewById(R.id.createNew);
        search = (FloatingActionButton)findViewById(R.id.search);

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewTroop.class));
                finish();
            }
        });
    }
}