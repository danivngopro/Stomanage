package com.example.stomanage;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManagerMainScreen extends AppCompatActivity {

    TextView _name;
    Button _register,_button,_button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main_screen);

        _name = (TextView)findViewById(R.id.NameText);
        //_register = (Button)findViewById(R.id.registr);
        //_button = (Button)findViewById(R.id.button);
        _button2 = (Button)findViewById(R.id.button2);

        _name.setText("שלום " + "ירין");

    }
}