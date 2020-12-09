package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.stomanage.firebase.dataObject.FactoryObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewOrdersToFactory extends AppCompatActivity {

    String factoryKey, factoryName;
    ArrayList<String> Uids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders_to_factory);

        Uids = new ArrayList<>();

        Intent intent = getIntent();
        factoryKey = intent.getStringExtra("factorySelectedKey");
        factoryName = intent.getStringExtra("factorySelected");

        printItems();

        detectItemClickedFromList();
    }

    private void printItems() {
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = DBRef.child("Factories").child(factoryKey).child("orders");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> orders = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    orders.add(uid);
                    Uids.add(uid);
                }
                ListView listView = (ListView) findViewById(R.id.Orderslist);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(viewOrdersToFactory.this, android.R.layout.simple_list_item_1,orders);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void detectItemClickedFromList() {
        ListView listView = (ListView) findViewById(R.id.Orderslist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = listView.getItemAtPosition(position).toString();

                Intent intent = new Intent(viewOrdersToFactory.this, ManagerViewOrder.class);
                intent.putExtra("factoryKey",factoryKey);
                intent.putExtra("uid",Uids.get(position));
                startActivity(intent);
            }
        });
    }
}