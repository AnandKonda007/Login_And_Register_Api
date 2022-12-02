package com.example.loginandregisterapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
public class UpdatePasswordActivity extends AppCompatActivity {
    EditText userid, newpassword, confirmpassword;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        userid = findViewById(R.id.userid2);
        submit=findViewById(R.id.submit);
        newpassword = findViewById(R.id.New_password);
        confirmpassword = findViewById(R.id.confirm_Password);
        userid.setEnabled(false);
        Intent intent = getIntent();
        String str = intent.getStringExtra("userID");
        userid.setText(str);


    }
}