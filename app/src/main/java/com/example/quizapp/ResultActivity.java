package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String userName = getIntent().getStringExtra("USER_NAME");
        int score       = getIntent().getIntExtra("SCORE", 0);
        int total       = getIntent().getIntExtra("TOTAL", 5);

        TextView tvCongrats = findViewById(R.id.tvCongrats);
        TextView tvScore    = findViewById(R.id.tvScore);
        Button btnNewQuiz   = findViewById(R.id.btnNewQuiz);
        Button btnFinish    = findViewById(R.id.btnFinish);

        tvCongrats.setText("Well done, " + userName + "!");
        tvScore.setText("Your Score: " + score + " / " + total);

        // Subtask 3: go back to main screen WITH name pre-filled
        btnNewQuiz.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("USER_NAME", userName);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        // Subtask 3: close the entire app
        btnFinish.setOnClickListener(v -> finishAffinity());
    }
}