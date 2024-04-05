package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String,String> loginInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        IDAndPasswords idAndPasswords = new IDAndPasswords();
        loginInfo = idAndPasswords.originalLoginInfo;
        TextView loginTxt = findViewById(R.id.txtLogin);
        loginTxt.setText(R.string.login_text);
    }

    public void onLoginClick(View view){
        EditText editUserId = findViewById(R.id.editTextUserID);
        EditText editPassword = findViewById(R.id.editTextUserPassword);
        TextView credentialsValidator = findViewById(R.id.credentialsValidator);
        String id = editUserId.getText().toString();
        String password = editPassword.getText().toString();

        if (loginInfo.containsKey(id)){
            if (loginInfo.get(id).equals(password)){
                credentialsValidator.setTextColor(Color.GREEN);
                credentialsValidator.setText("Logged in Successfully");
            }
            else {
                credentialsValidator.setTextColor(Color.RED);
                credentialsValidator.setText("Wrong Password");
            }
        }
        else {
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("Wrong UserId");
        }

    }

    public void onSignUpClick(View view){
        EditText editUserId = findViewById(R.id.editTextUserID);
        EditText editPassword = findViewById(R.id.editTextUserPassword);
        TextView credentialsValidator = findViewById(R.id.credentialsValidator);
        String id = editUserId.getText().toString();
        String password = editPassword.getText().toString();

        if (!loginInfo.containsKey(id)){
            loginInfo.put(id,password);
            credentialsValidator.setTextColor(Color.GREEN);
            credentialsValidator.setText("Signed up Successfully");
        }
        else {
            credentialsValidator.setTextColor(Color.RED);
            credentialsValidator.setText("User Id already exist");
        }
    }

}