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

import com.example.stomanage.firebase.dataObject.FactoryObj;
import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class viewOpenFactoriesForOrders extends AppCompatActivity {

    ArrayList<String> kay;
    UserObj user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_personal_lists);

        Intent intent = getIntent();
        user = (UserObj)intent.getSerializableExtra("user");

        kay = new ArrayList<>();

        printItems();

        detectItemClickedFromList();

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
                    if (factory.get_troop().equals(user.getTroop())){
                        String factoryKey = ds.getKey().toString();
                        kay.add(factoryKey);
                        items.add(factory.get_name());
                    }
                }
                ListView listView = (ListView) findViewById(R.id.listview1);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(viewOpenFactoriesForOrders.this, android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    public void detectItemClickedFromList() {
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = listView.getItemAtPosition(position).toString();

                //send intent with user details and group names.

                Intent intent;

                intent = new Intent(getApplicationContext(), FactoryitemList.class);
                intent.putExtra("user", (Serializable)user);
                intent.putExtra("factoryChosen", kay.get(position));
                startActivity(intent);


            }
        });
    }
}