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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TroopManagerScreen extends AppCompatActivity {

    FloatingActionButton createNew, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troop_manager_screen);

        createNew = (FloatingActionButton)findViewById(R.id.createNew);
        search = (FloatingActionButton)findViewById(R.id.search);

        printItems();
        createNewTroopAction();
    }


    private void printItems() {
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
                ListView listView = (ListView) findViewById(R.id.list_view);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TroopManagerScreen.this, android.R.layout.simple_list_item_1,items);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    private void createNewTroopAction(){
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                dialog = new Dialog(TroopManagerScreen.this);
                dialog.setContentView(R.layout.activity_pop_up_new_troop);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.getWindow().getAttributes().windowAnimations = R.style.popUpAnimation;
                dialog.show();

                Button addButton = (Button)dialog.findViewById(R.id.addButton);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText troopName = (EditText)(dialog.findViewById(R.id.troopName));
                        String troop = troopName.getText().toString().trim();
                        if (troop.length() == 0) {
                            troopName.setError("Name is required.");
                            troopName.requestFocus();
                        }
                        else {
                            writeNewTroopToDB(troop);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }


    private void writeNewTroopToDB(String troop) {
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        String key = DBRef.push().getKey();
        DBRef.child("troops list").child(key).setValue(troop);
    }

}