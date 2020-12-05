package com.example.stomanage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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



//    public void printItems() {
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference ref = rootRef.child("items list");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<String> items = new ArrayList<>();
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String item = ds.child("item").getValue(String.class);
//                    items.add(item);
//                }
//                ListView listView = (ListView) findViewById(R.id.listview1);
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(itemList.this, android.R.layout.simple_list_item_1,items);
//                listView.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        ref.addListenerForSingleValueEvent(valueEventListener);
//    }

    public void printItems() {
        DatabaseReference databaseReference;
        ListView listView;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("items list");
        listView = (ListView) findViewById(R.id.listview1);
        arrayAdapter = new ArrayAdapter<String>(itemList.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String item = snapshot.child("item").getValue(String.class);
                arrayList.add(item);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String item = snapshot.child("item").getValue(String.class);
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
//        mDatabase.child("items list").push().child("item").setValue("a");
//        mDatabase.child("items list").push().child("item").setValue("b");
//        mDatabase.child("items list").push().child("item").setValue("c");
//        mDatabase.child("items list").push().child("item").setValue("d");
//        mDatabase.child("items list").push().child("item").setValue("e");
//        mDatabase.child("items list").push().child("item").setValue("f");
//        mDatabase.child("items list").push().child("item").setValue("g");
//        mDatabase.child("items list").push().child("item").setValue("h");

        mDatabase.child("userPrivateList").child(id).child(activityName).child(itemName).setValue(amount);
    }

    public void detectItemClickedFromList() {
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valueSelected = listView.getItemAtPosition(position).toString();

                Dialog dialog;
                dialog = new Dialog(itemList.this);
                dialog.setContentView(R.layout.activity_pop_up);
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
                        EditText groupName = (EditText)(dialog.findViewById(R.id.GroupName));
                        String amount = amountText.getText().toString();
                        String group = groupName.getText().toString();
                        if(amount.trim().length() > 0) {
                            int amountinteger = Integer.parseInt(amount);
                            Toast.makeText(itemList.this, "item Added!", Toast.LENGTH_SHORT).show();
                            addValueToFirebase(group, valueSelected, amountinteger);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(itemList.this, "please enter an amount", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }



}
