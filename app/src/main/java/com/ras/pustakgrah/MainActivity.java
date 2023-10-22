package com.ras.pustakgrah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ras.pustakgrah.Adapters.BookAdapter;
import com.ras.pustakgrah.Models.BookModels;
import com.ras.pustakgrah.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<BookModels> list = new ArrayList<>();
    ActivityMainBinding binding;
    ProgressDialog progress;
    Button signOut_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signOut_btn = findViewById(R.id.signOut_btn);
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait While Loading....");
        progress.show();
        create_database();
        signOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, Email_Auth.class));
                finish();
            }
        });
    }

    public void create_database() {

        BookAdapter adapter = new BookAdapter(list, MainActivity.this);
        binding.recyclerView.setAdapter(adapter);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//       LinearLayoutManager linearLayoutManager1 = new Li nearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        binding.recyclerView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
//       binding.recyclerView.setLayoutManager(staggeredGridLayoutManager) ;

        FirebaseDatabase.getInstance().getReference().child("books")
                .addValueEventListener(new ValueEventListener() {
                    //Here we are creating a child for database with the help of creating instance of firebase in backend code.
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        list.clear();

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            BookModels model = dataSnapshot1.getValue(BookModels.class);
                            list.add(model);
                        }

                        adapter.notifyDataSetChanged();
                        progress.dismiss();
                        Toast.makeText(MainActivity.this, "Keep Your Light Mode ON to Read books", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}