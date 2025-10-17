package com.example.projecthome;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // basic view references
        RecyclerView recyclerView = findViewById(R.id.recyclerTasks);
        TextView tvCurrentTab = findViewById(R.id.tvCurrentTab);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView tvProgressPercent = findViewById(R.id.tvProgressPercent);
    }
}
