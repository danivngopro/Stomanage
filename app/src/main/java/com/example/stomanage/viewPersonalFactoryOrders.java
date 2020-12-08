package com.example.stomanage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class viewPersonalFactoryOrders extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_personal_list_items);

        printItems();

        detectItemClickedFromList();

    }

    public void printItems() {
        DatabaseReference databaseReference;
        ListView listView;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");
        String id = user.getId();
        String listID = intent.getStringExtra("listChosen");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Factories").child(listID).child(id);
        listView = (ListView) findViewById(R.id.listview1);
        arrayAdapter = new ArrayAdapter<String>(viewPersonalFactoryOrders.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String item = snapshot.getKey() + ": " + snapshot.getValue();
                    arrayList.add(item);
                    arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intent intent = getIntent();
                UserObj user = (UserObj)intent.getSerializableExtra("user");

                intent = new Intent(getApplicationContext(), viewPersonalFactoryOrders.class);
                intent.putExtra("user", (Serializable)user);
                intent.putExtra("listChosen", listID);
                startActivity(intent);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String item = snapshot.getKey() + ": " + snapshot.getValue();
                arrayList.remove(item);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addValueToFirebase(String activityName, String itemName, int amount) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");
        String id = user.getId();

        mDatabase.child("Factories").child(activityName).child(id).child(itemName).setValue(amount);
    }

    public void removeValueFromFirebase(String activityName, String itemName) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        UserObj user = (UserObj)intent.getSerializableExtra("user");
        String id = user.getId();

        mDatabase.child("Factories").child(activityName).child(id).child(itemName).setValue(null);
    }

    public void detectItemClickedFromList() {
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = listView.getItemAtPosition(position).toString().split(":")[0];

                Dialog dialog;
                dialog = new Dialog(viewPersonalFactoryOrders.this);
                dialog.setContentView(R.layout.activity_pop_up_edit_item);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.getWindow().getAttributes().windowAnimations = R.style.popUpAnimation;
                dialog.show();


                Button addProductButton = dialog.findViewById(R.id.addProductButton);
                addProductButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText amountText = (EditText)(dialog.findViewById(R.id.AmountOfProduct));
                        String amount = amountText.getText().toString();
                        Intent intent = getIntent();
                        String group = intent.getStringExtra("listChosen");
                        if(amount.trim().length() > 0) {
                            int amountinteger = Integer.parseInt(amount);
                            Toast.makeText(viewPersonalFactoryOrders.this, "item updated!", Toast.LENGTH_SHORT).show();
                            addValueToFirebase(group, valueSelected, amountinteger);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(viewPersonalFactoryOrders.this, "please enter an amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button removeItemButton = dialog.findViewById(R.id.removeItemButton);
                removeItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        String group = intent.getStringExtra("listChosen");
                        Toast.makeText(viewPersonalFactoryOrders.this, "item removed", Toast.LENGTH_SHORT).show();
                        removeValueFromFirebase(group, valueSelected);
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}