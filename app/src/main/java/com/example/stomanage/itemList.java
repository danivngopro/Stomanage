package com.example.stomanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.example.stomanage.firebase.model.FirebaseDBUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class itemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        printItems();

        detectItemClickedFromList();

    }



    public void printItems() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("items list");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> items = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String item = ds.child("item").getValue(String.class);
                    items.add(item);
                }
                ListView listView = (ListView) findViewById(R.id.listview1);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(itemList.this, android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void addValueToFirebase(String value) {
        DatabaseReference mDatabase;
// get reference to your Firebase Database.
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");
        String id = user.getId();

        mDatabase.child("userPrivateList").child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                int counter = 1;
                boolean foundKey = false;
                for(DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.toString().split("key = ")[1].split(",")[0].equals(value)) {
                        counter = Integer.parseInt(ds.toString().split("value = ")[1].split(" ")[0]) + 1;

                        mDatabase.child("userPrivateList").child(id).child(value).setValue(counter);
                        foundKey = true;
                        return;
                    }
                }
                if(!foundKey) mDatabase.child("userPrivateList").child(id).child(value).setValue(counter);
                return;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void detectItemClickedFromList() {
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = listView.getItemAtPosition(position).toString();

                addValueToFirebase(valueSelected);

            }
        });
    }



}
