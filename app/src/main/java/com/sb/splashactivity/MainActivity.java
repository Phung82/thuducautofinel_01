package com.sb.splashactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(20);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser==null){
            Intent registerIntent = new Intent(MainActivity.this, Register_Activity.class);
            startActivity(registerIntent);
            finish();
        }else{
            Intent mainIntent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(mainIntent);
            finish();
        }
    }
}