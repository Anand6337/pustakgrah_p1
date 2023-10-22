package com.ras.pustakgrah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ras.pustakgrah.authFiles.verification_activity;

public class Signup_Activity extends AppCompatActivity {

    Button getOTP_btn;
    EditText phnNumber_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        getOTP_btn = findViewById(R.id.getOTP_btn);
        phnNumber_input = findViewById(R.id.input_email);

        getOTP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobNo = phnNumber_input.getText().toString();
                if (mobNo.startsWith("+91"))
                {
                    Intent intent = new Intent(Signup_Activity.this, verification_activity.class);
                    intent.putExtra("mobile Number", mobNo);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Signup_Activity.this, "Please Insert Country Code with Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}