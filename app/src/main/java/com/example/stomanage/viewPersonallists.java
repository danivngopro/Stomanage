package com.example.stomanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.example.stomanage.viewPersonalListItems;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class viewPersonallists extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_personal_lists);

        printItems();

        detectItemClickedFromList();

    }


    public void printItems() {
        DatabaseReference databaseReference;
        ListView listView;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("userPrivateList");
        listView = (ListView) findViewById(R.id.listview1);
        arrayAdapter = new ArrayAdapter<String>(viewPersonallists.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String item = ds.getKey();
                    arrayList.add(item);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intent intent = getIntent();
                UserObj user = (UserObj)intent.getSerializableExtra("user");

                intent = new Intent(getApplicationContext(), viewPersonallists.class);
                intent.putExtra("user", (Serializable)user);
                startActivity(intent);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String item = ds.getKey();
                    arrayList.add(item);
                    arrayAdapter.notifyDataSetChanged();
                }
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

                //send intent with user details and group names.

                Intent intent = getIntent();
                UserObj user = (UserObj)intent.getSerializableExtra("user");

                intent = new Intent(getApplicationContext(), viewPersonalListItems.class);
                intent.putExtra("user", (Serializable)user);
                intent.putExtra("listChosen", valueSelected);
                startActivity(intent);


            }
        });
    }
}