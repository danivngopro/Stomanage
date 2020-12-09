package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerViewOrder extends AppCompatActivity {

    String factoryKey,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_order);

        Intent intent = getIntent();
        factoryKey = intent.getStringExtra("factoryKey");
        uid = intent.getStringExtra("uid");

        printItems();
    }


    private void printItems() {
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = DBRef.child("Factories").child(factoryKey).child("orders").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> order = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String item = ds.getKey().toString() + "\t\t" + ds.getValue().toString();
                    order.add(item);
                }
                ListView listView = (ListView) findViewById(R.id.Orderlist);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManagerViewOrder.this, android.R.layout.simple_list_item_1,order);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }
}