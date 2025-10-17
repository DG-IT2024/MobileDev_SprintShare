package com.example.projecthome;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProjectHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_home);

        TextView tvSelectedProject = findViewById(R.id.tvSelectedProject);
        ImageView iconDropdown = findViewById(R.id.iconDropdown);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProjects);
        FloatingActionButton fab = findViewById(R.id.fabAddProject);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        ImageView btnFilter = findViewById(R.id.btnFilter);
    }
}
