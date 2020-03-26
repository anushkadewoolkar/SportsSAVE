package com.example.sportssave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {
    FirebaseFirestore db;
    EditText age;
    Button submit;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        db = FirebaseFirestore.getInstance();
        age = (EditText) findViewById(R.id.ageEditText);
        submit = (Button) findViewById(R.id.submitButton);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAgeToFirebase();
            }
        });

    }
    private void updateAgeToFirebase(){
        db = FirebaseFirestore.getInstance();
        String ageNum = age.getText().toString().trim();
        String userUID = mAuth.getCurrentUser().getUid();
        String gameID = "0";
        String name = "Bob";
        Map<String, Object> user = new HashMap<>();
        user.put("currentGame", gameID);
        user.put("name", name);
        user.put("age", ageNum);
        db.collection("users").document(userUID).collection("stuff").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Test", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserInfoActivity.this, "Error adding documents", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
