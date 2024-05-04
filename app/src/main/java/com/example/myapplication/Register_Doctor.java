package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class Register_Doctor extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    String specialityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_doctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        Spinner speciality = findViewById(R.id.speciality);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Cardiologist");
        arrayList.add("Dentist");
        arrayList.add("Psychiatrist");
        arrayList.add("Pediatrician");
        arrayList.add("Surgeon");
        arrayList.add("Neurologist");
        arrayList.add("ENT");
        arrayList.add("Opthalmologist");
        arrayList.add("Gynaecologist");
        arrayList.add("Orthopedist");
        arrayList.add("Gastroenterologist");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciality.setAdapter(arrayAdapter);
        speciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialityName = parent.getItemAtPosition(position).toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void onSignUpClick(View view){
        EditText editUserEmailAddress = findViewById(R.id.editTextUserEmailAddress);
        EditText editPassword = findViewById(R.id.editTextUserPassword);
        EditText editUsername = findViewById(R.id.editTextUsername);
        EditText editPmdcNo = findViewById(R.id.editTextUserPMDC);
        TextView credentialsValidator = findViewById(R.id.credentialsValidator);
        String username = String.valueOf(editUsername.getText());
        String email = String.valueOf(editUserEmailAddress.getText());
        String password = String.valueOf(editPassword.getText());
        String pmdcNo = String.valueOf(editPmdcNo.getText());
        String doctorSpeciality = String.valueOf(specialityName);

        if (TextUtils.isEmpty(email)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter an email");
        }
        if (TextUtils.isEmpty(password)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter a password");
        }
        if (TextUtils.isEmpty(pmdcNo)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter your PMDC Number");
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register_Doctor.this, "Signed Up Successfully.",
                                    Toast.LENGTH_SHORT).show();
                            assert user != null;
                            String userid = user.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("role", "doctor");

                            reference.setValue(hashMap);

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("DoctorDetails");

                            HashMap<String, String> hashMap2 = new HashMap<>();
                            hashMap2.put("pmdcNo", pmdcNo);
                            hashMap2.put("speciality", doctorSpeciality);

                            reference.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register_Doctor.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void onAlreadyRegistered(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

}