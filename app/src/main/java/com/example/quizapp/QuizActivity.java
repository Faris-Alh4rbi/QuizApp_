package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    // ── Quiz questions about SIT305 / Mobile App Development ──
    String[] questions = {
            "What language is primarily used to build Android apps in SIT305?",
            "Which file defines the UI layout of an Android screen?",
            "What component is used to switch between screens in Android?",
            "Which tool is used to develop Android apps in SIT305?",
            "What does XML stand for in Android development?"
    };

    String[][] options = {
            {"Python",          "Java",              "Swift",          "C++"},
            {"MainActivity.java", "styles.xml",      "activity_main.xml", "build.gradle"},
            {"Fragment",        "Intent",            "Service",        "BroadcastReceiver"},
            {"Eclipse",         "Xcode",             "Android Studio", "VS Code"},
            {"Extra Markup Language", "Extensible Markup Language",
                    "Extended Machine Language", "External Module Link"}
    };

    // Correct answer index for each question (0-based)
    int[] correct = {1, 2, 1, 2, 1};

    // ── State ──
    int  currentQ   = 0;
    int  score      = 0;
    int  selected   = -1;
    boolean submitted = false;
    String userName;

    // ── Views ──
    ProgressBar progressBar;
    TextView    tvProgress, tvQuestion;
    Button[]    opts = new Button[4];
    Button      btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName    = getIntent().getStringExtra("USER_NAME");
        progressBar = findViewById(R.id.progressBar);
        tvProgress  = findViewById(R.id.tvProgress);
        tvQuestion  = findViewById(R.id.tvQuestion);
        opts[0]     = findViewById(R.id.btnOpt0);
        opts[1]     = findViewById(R.id.btnOpt1);
        opts[2]     = findViewById(R.id.btnOpt2);
        opts[3]     = findViewById(R.id.btnOpt3);
        btnSubmit   = findViewById(R.id.btnSubmit);

        loadQuestion();

        opts[0].setOnClickListener(v -> pick(0));
        opts[1].setOnClickListener(v -> pick(1));
        opts[2].setOnClickListener(v -> pick(2));
        opts[3].setOnClickListener(v -> pick(3));

        btnSubmit.setOnClickListener(v -> {
            if (!submitted) {
                // ── SUBMIT ──────────────────────────────────────
                if (selected == -1) {
                    Toast.makeText(this, "Pick an answer first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                submitted = true;

                // Subtask 1: colour correct green, wrong red
                if (selected == correct[currentQ]) {
                    score++;
                    opts[selected].setBackgroundColor(Color.GREEN);
                } else {
                    opts[selected].setBackgroundColor(Color.RED);
                    opts[correct[currentQ]].setBackgroundColor(Color.GREEN);
                }

                // Subtask 1: lock all answer buttons
                for (Button b : opts) b.setEnabled(false);

                btnSubmit.setText(currentQ < questions.length - 1 ? "NEXT" : "SEE RESULTS");

            } else {
                // ── NEXT / GO TO RESULTS ─────────────────────────
                currentQ++;
                if (currentQ < questions.length) {
                    loadQuestion();
                } else {
                    Intent i = new Intent(this, ResultActivity.class);
                    i.putExtra("USER_NAME", userName);
                    i.putExtra("SCORE", score);
                    i.putExtra("TOTAL", questions.length);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    // Load / reset the current question
    void loadQuestion() {
        submitted = false;
        selected  = -1;
        btnSubmit.setText("SUBMIT");

        // Subtask 2: update progress bar
        int pct = (int) ((currentQ / (float) questions.length) * 100);
        progressBar.setProgress(pct);
        tvProgress.setText("Question " + (currentQ + 1) + " / " + questions.length + "  (" + pct + "%)");

        tvQuestion.setText(questions[currentQ]);

        for (int i = 0; i < 4; i++) {
            opts[i].setText(options[currentQ][i]);
            opts[i].setBackgroundColor(Color.parseColor("#6200EE")); // default purple
            opts[i].setTextColor(Color.WHITE);
            opts[i].setEnabled(true);
        }
    }

    // Highlight the chosen answer orange
    void pick(int idx) {
        if (submitted) return;
        selected = idx;
        for (int i = 0; i < 4; i++) {
            opts[i].setBackgroundColor(i == idx
                    ? Color.parseColor("#FF9800")   // orange = selected
                    : Color.parseColor("#6200EE")); // purple = default
        }
    }
}