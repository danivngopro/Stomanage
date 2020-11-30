package com.example.stomanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.stomanage.firebase.FirebaseBaseModel;
import com.example.stomanage.firebase.dataObject.UserObj;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewTroop extends AppCompatActivity {

    EditText name;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_troop);

        name = findViewById(R.id.editTextTname);
        add = findViewById(R.id.buttonAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sname = name.getText().toString();
                if (Sname.isEmpty()){
                    name.setError("please enter name.");
                    name.requestFocus();
                    return;
                }
                writeNewTroopToDB(Sname);
                finish();
            }
        });
    }

    private void writeNewTroopToDB (String troop){
        DatabaseReference DBRef = FirebaseDatabase.getInstance().getReference();
        String key = DBRef.push().getKey();
        DBRef.child("troops").child(key).setValue(troop);
    }
}