package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.stomanage.firebase.dataObject.FactoryObj;
import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FactoryManagementScreen extends AppCompatActivity {

    FloatingActionButton createNew, search;
    ArrayList<String> key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_management_screen);

        key = new ArrayList<>();

        createNew = findViewById(R.id.createNew);
        search = findViewById(R.id.search);

        printItems();

        detectItemClickedFromList();


        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FactoryManagementScreen.this,NewFactory.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void printItems() {
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = DBRef.child("Factories");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> items = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    FactoryObj factory = ds.getValue(FactoryObj.class);
                    String factoryKey = ds.getKey().toString();
                    key.add(factoryKey);
                    items.add(factory.get_name());
                }
                ListView listView = (ListView) findViewById(R.id.list_view);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FactoryManagementScreen.this, android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }


    public void detectItemClickedFromList() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = listView.getItemAtPosition(position).toString();

                Dialog dialog;
                dialog = new Dialog(FactoryManagementScreen.this);
                dialog.setContentView(R.layout.acrivity_pop_up_factory_options);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.getWindow().getAttributes().windowAnimations = R.style.popUpAnimation;
                dialog.show();

                Button orders, close;
                orders = (Button)(dialog.findViewById(R.id.orders));
                close = (Button)(dialog.findViewById(R.id.close));

                orders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(FactoryManagementScreen.this, viewOrdersToFactory.class);
                        intent.putExtra("factorySelected",valueSelected);
                        intent.putExtra("factorySelectedKey",key.get(position));
                        startActivity(intent);

                    }
                });
            }
        });
    }

}