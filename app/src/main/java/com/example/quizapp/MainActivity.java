package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName   = findViewById(R.id.etName);
        Button   btnStart = findViewById(R.id.btnStart);

        // Subtask 3 – keep name when returning from Results
        String savedName = getIntent().getStringExtra("USER_NAME");
        if (savedName != null) {
            etName.setText(savedName);
        }

        btnStart.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(this, QuizActivity.class);
            i.putExtra("USER_NAME", name);
            startActivity(i);
        });
    }
}