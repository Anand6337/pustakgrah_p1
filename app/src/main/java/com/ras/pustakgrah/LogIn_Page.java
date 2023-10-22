package com.ras.pustakgrah;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ras.pustakgrah.databinding.ActivityLogInPageBinding;

public class LogIn_Page extends AppCompatActivity {
    ActivityLogInPageBinding binding;
    EditText email_input, pass_input;
    Button google_btn, signIn_btn;
    private FirebaseAuth auth;
    private BeginSignInRequest signInRequest;
    private GoogleSignInClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInPageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_log_in_page);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        google_btn = findViewById(R.id.googleBtn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, gso);
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = client.getSignInIntent();
        //Here we are going to second activity and returning back with the user data
//        startActivityForResult(signInIntent, RC_SIGN_IN);
        startActivityForResult(signInIntent, 1234);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*Now the data returning from launching the intent of Google Sign-in (Data as the Profile Pic, Email Address)
       from GoogleSignInApi.getSignInIntent();
         */

        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Successful SignIn with Google Authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d("TAG", "FirebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn_Page.this, "Google Sign In Successful", Toast.LENGTH_LONG).show();
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(LogIn_Page.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w("TAG", "signInWithCredential:fail");
                            Toast.makeText(LogIn_Page.this,
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(binding.getRoot(), "Authentication Failed", Snackbar.LENGTH_SHORT);
                        }
                    }
                });
    }

}