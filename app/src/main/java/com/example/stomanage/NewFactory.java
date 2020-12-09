package com.example.stomanage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stomanage.firebase.dataObject.FactoryObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NewFactory extends AppCompatActivity {

    DatePickerDialog picker;
    EditText factoryName, date;
    Spinner troop;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_factory);

        getComponentFromXml();

        setTroopSpinner();

        selectDateButton();

        addButton();

    }

    private void getComponentFromXml(){
        factoryName = (EditText)(findViewById(R.id.FactoryName));
        date = (EditText)(findViewById(R.id.EditTextDate));
        troop = (Spinner)(findViewById(R.id.spinnerTroop));
        add = (Button)(findViewById(R.id.addProductButton));
    }

    private void setTroopSpinner(){
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = DBRef.child("troops list");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> items = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String item = ds.getValue(String.class);
                    items.add(item);
                }

                ArrayAdapter<String> troopadapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, items);
                troop.setAdapter(troopadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    private void selectDateButton(){
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NewFactory.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void addButton(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FactoryObj factory = new FactoryObj(factoryName.getText().toString(),
                        troop.getSelectedItem().toString(),
                        date.getText().toString());

                if (factory.get_name().isEmpty()) {
                    factoryName.setError("נדרש שם");
                    factoryName.requestFocus();
                    return;
                }
                if (factory.get_date().isEmpty()) {
                    date.setError("נדרש תאריך");
                    date.requestFocus();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Factory Created", Toast.LENGTH_SHORT).show();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                String key = mDatabase.push().getKey();
                mDatabase.child("Factories").child(key).setValue(factory);
                Intent intent = new Intent(NewFactory.this,FactoryManagementScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

