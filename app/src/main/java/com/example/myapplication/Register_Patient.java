package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;

public class Register_Patient extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_patient);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public void onSignUpClick(View view){
        EditText editUserEmailAddress = findViewById(R.id.editTextUserEmailAddress);
        EditText editPassword = findViewById(R.id.editTextUserPassword);
        EditText editUsername = findViewById(R.id.editTextUsername);
        EditText editAge = findViewById(R.id.editTextUserAge);
        EditText editGender = findViewById(R.id.editTextUserGender);
        TextView credentialsValidator = findViewById(R.id.credentialsValidator);
        String username = String.valueOf(editUsername.getText());
        String email = String.valueOf(editUserEmailAddress.getText());
        String password = String.valueOf(editPassword.getText());
        String age = String.valueOf(editAge.getText());
        String gender = String.valueOf(editGender.getText());

        if (TextUtils.isEmpty(email)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter an email");
        }
        if (TextUtils.isEmpty(password)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter a password");
        }
        if (TextUtils.isEmpty(age)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter an age");
        }
        if (TextUtils.isEmpty(gender)){
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Enter a gender");
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register_Patient.this, "Signed Up Successfully.",
                                    Toast.LENGTH_SHORT).show();
                            assert user != null;
                            String userid = user.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("role", "patient");

                            reference.setValue(hashMap);

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid).child("PatientDetails");

                            HashMap<String, String> hashMap2 = new HashMap<>();
                            hashMap2.put("Age", age);
                            hashMap2.put("Gender", gender);

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
                            Toast.makeText(Register_Patient.this, "Authentication failed.",
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