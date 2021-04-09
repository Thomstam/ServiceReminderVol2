package com.example.servicereminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.servicereminder.FormsPackage.FormSetup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_FORM_SETUP = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newFormSetup();
    }

    private void newFormSetup() {
        FloatingActionButton floatingActionButton = findViewById(R.id.newFormButton);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormSetup.class);
            startActivityForResult(intent, REQUEST_FORM_SETUP);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}