package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Book_Appointment extends AppCompatActivity {
    EditText when;
    EditText where;
    EditText how;;
    EditText doctorSpecialty;


    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        when=findViewById(R.id.when);
        where=findViewById(R.id.where);
        how=findViewById(R.id.how);
        doctorSpecialty=findViewById(R.id.specialty);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("PatientDetails");
    }
    public void onBookClick(View view){
        String briefProblemDescription = "When problem occurred: " + String.valueOf(when.getText()) + " | Where are you feeling pain: " + String.valueOf(where.getText()) + " | How did the problem happen: " + String.valueOf(how.getText());
        String doctorSpeciality = String.valueOf(doctorSpecialty.getText());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!briefProblemDescription.equals("") && !doctorSpeciality.equals("")){
                    bookAnAppointment(firebaseUser.getUid(), snapshot.child("Age").getValue(String.class),snapshot.child("Gender").getValue(String.class) , briefProblemDescription, doctorSpeciality);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        Toast.makeText(Book_Appointment.this, "Booked an appointment.",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Dashboard_Patient.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    public void onRecommendClick(View view){
        Intent intent = new Intent(getApplicationContext(), Recommend_Doctor.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    public void onBackClick(View view){
        Intent intent = new Intent(getApplicationContext(), Dashboard_Patient.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void bookAnAppointment(String patientUid, String age, String gender, String briefProblemDescription, String doctorSpeciality){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("patientUid",patientUid);
        hashMap.put("age",age);
        hashMap.put("gender",gender);
        hashMap.put("briefProblemDescription",briefProblemDescription);
        hashMap.put("doctorSpeciality", doctorSpeciality);

        reference.child("AppointmentRequests").push().setValue(hashMap);
    }
}
