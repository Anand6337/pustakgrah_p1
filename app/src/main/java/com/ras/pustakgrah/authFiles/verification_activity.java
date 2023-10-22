package com.ras.pustakgrah.authFiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ras.pustakgrah.MainActivity;
import com.ras.pustakgrah.R;

import java.util.concurrent.TimeUnit;

public class verification_activity extends AppCompatActivity {

    Button verify_btn;
    EditText otp_input;
    String phoneNumber , otpid;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        getSupportActionBar().hide();
        phoneNumber = getIntent().getStringExtra("mobile Number").toString();
        verify_btn = findViewById(R.id.verify_btn);
        otp_input = findViewById(R.id.otp_input);
        mAuth = FirebaseAuth.getInstance();
        generateOTP();

    verify_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (otp_input.getText().toString().isEmpty())

                Toast.makeText(getApplicationContext(), "Blank Field isn't allow", Toast.LENGTH_SHORT).show();
            else if(otp_input.getText().length()!=6)
                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
            else
            {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, otp_input.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }

        }
    });
    }

    private void generateOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,
                60, TimeUnit.SECONDS, verification_activity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }
                    //BELOW METHOD WILL DIRECTLY CALL IF USER SIGN IN WITH SAME PHONE........
                    // OTHERWISE IT'LL REDIRECT TO GENERATE otp FUNCTION CALLING IN ON CREATE METHOD.
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(verification_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                })  ;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(verification_activity.this, "Verify Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(verification_activity.this, MainActivity.class));
                            finish();
                        }
                        else {

                            Toast.makeText(verification_activity.this, "Incorrect OTP", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}