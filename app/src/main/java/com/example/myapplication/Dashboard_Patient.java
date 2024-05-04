package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard_Patient extends AppCompatActivity {

        CircleImageView profile_image;
        TextView username;
        FirebaseUser firebaseUser;
        DatabaseReference reference;
        Button recommend;
        Button book;
        Button chat;
        Button current;

        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                EdgeToEdge.enable(this);
                setContentView(R.layout.activity_dashboard);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("");
                recommend=findViewById(R.id.button8);
                current=findViewById(R.id.button11);
                book=findViewById(R.id.button9);
                chat=findViewById(R.id.button10);

                profile_image = findViewById(R.id.profile_image);
                username = findViewById(R.id.username);

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                username.setText(user.getUsername());
                                if (user.getImageURL().equals("default")){
                                        profile_image.setImageResource(R.mipmap.ic_launcher);
                                }
                                else {
                                        Glide.with(Dashboard_Patient.this).load(user.getImageURL()).into(profile_image);
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });



                chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), Tablayout.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        }
                });
                book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), Book_Appointment.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        }
                });
                recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), Recommend_Doctor.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        }
                });
                current.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), Current_Appointments.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        }
                });
        }
        public boolean onCreateOptionsMenu (Menu menu){
                getMenuInflater().inflate(R.menu.menu2, menu);
                return true;
        }
        public boolean onOptionsItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                        return true;
                }
                return super.onOptionsItemSelected(item);
        }

}
