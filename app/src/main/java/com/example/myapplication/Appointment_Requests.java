package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AppointmentRequestAdapter;
import com.example.myapplication.Model.AppointmentRequest;
import com.example.myapplication.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Appointment_Requests extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppointmentRequestAdapter appointmentRequestAdapter;
    private List<AppointmentRequest> mAppointmentRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAppointmentRequests = new ArrayList<>();
        readAppointmentRequests();
    }

    private void readAppointmentRequests(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String doctorsSpeciality = snapshot.child("DoctorDetails").child("speciality").getValue(String.class);
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("AppointmentRequests");
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mAppointmentRequests.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            AppointmentRequest appointmentRequest = dataSnapshot.getValue(AppointmentRequest.class);
                            assert appointmentRequest != null;
                            if (appointmentRequest.getDoctorSpeciality().equals(doctorsSpeciality)){
                                mAppointmentRequests.add(appointmentRequest);
                            }
                        }
                        appointmentRequestAdapter = new AppointmentRequestAdapter(Appointment_Requests.this, mAppointmentRequests);
                        recyclerView.setAdapter(appointmentRequestAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}