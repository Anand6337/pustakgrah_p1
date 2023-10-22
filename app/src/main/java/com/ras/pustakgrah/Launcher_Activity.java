package com.ras.pustakgrah;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class Launcher_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();

        Thread thread = new Thread() {
            public void run() {
                try {

                    sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Launcher_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        thread.start();
    }
}