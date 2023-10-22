package com.ras.pustakgrah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ras.pustakgrah.Models.userModel;
import com.ras.pustakgrah.databinding.EmailauthBinding;

import java.util.Objects;

public class Email_Auth extends AppCompatActivity {

    EmailauthBinding binding;
    private FirebaseAuth mAuth;
    Button sign_upBtn;
    TextView guest_btn,login_btn;
    EditText email,pass,username;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = EmailauthBinding.inflate(getLayoutInflater());
        setContentView(R.layout.emailauth);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Declarations of this activity
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        sign_upBtn = findViewById(R.id.signUpbtn);
        username = findViewById(R.id.username);
        email = findViewById(R.id.emailAdd);
        pass = findViewById(R.id.user_password);
        guest_btn = findViewById(R.id.guest_btn);

        //On clicks of this activity
        login_btn = findViewById(R.id.loginscreen_btn);
        sign_upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_user();
            }
        });
        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Email_Auth.this, "Please wait a moment", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Email_Auth.this,Signup_Activity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Email_Auth.this, LoginActivity.class);
//                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("We are creating your account");

    }
    public void create_user() {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(
                email.getText().toString(),
                pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful())
                {

                    //Getting user inputs ......
                    userModel users = new userModel(username.getText().toString(),email.getText().toString(),pass.getText().toString());
                    //Now we'll get userId (UID) from firebase .........
                    String id = (task.getResult().getUser()).getUid();
                    //Now we'll store the sign in user data into the realtime database of firebase
                    database.getReference().child("Users").child(id).setValue(users);
                    //It means ..into the database we are storing the user values(id,name,pass) through the reference (for more we can visit realtime database into the firebase
                    Toast.makeText(Email_Auth.this, "Welcome to Pustak Grah", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Email_Auth.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Email_Auth.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}