package com.example.sprintshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ProjectHomeActivity extends AppCompatActivity {

    private List<String> projectList;
    private ProjectAdapter adapter;

    // UI references
    private TextView tvSelectedProject;
    private ImageView iconDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_home);

        tvSelectedProject = findViewById(R.id.tvSelectedProject);
        iconDropdown = findViewById(R.id.iconDropdown);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProjects);
        FloatingActionButton fab = findViewById(R.id.fabAddProject);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        ImageView btnFilter = findViewById(R.id.btnFilter); // added line

        projectList = new ArrayList<>();
        projectList.add("Mobile Dev");
        projectList.add("OOSAD");
        projectList.add("Social Professional Issues");

        adapter = new ProjectAdapter(projectList, position -> {
            String name = projectList.get(position);
            Toast.makeText(this, "Opened project: " + name, Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddProjectPopup(v));
        btnFilter.setOnClickListener(v -> showFilterPopup(v)); // use your icon button

        iconDropdown.setOnClickListener(this::showProjectDropdown);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_projects) return true;
            if (itemId == R.id.nav_account) { Toast.makeText(this, "Account selected", Toast.LENGTH_SHORT).show(); return true; }
//            if (itemId == R.id.nav_voting) { Toast.makeText(this, "Voting selected", Toast.LENGTH_SHORT).show(); return true; }
//            if (itemId == R.id.nav_calendar) { Toast.makeText(this, "Agenda selected", Toast.LENGTH_SHORT).show(); return true; }
            if (itemId == R.id.nav_tasks) { Toast.makeText(this, "Tasks selected", Toast.LENGTH_SHORT).show(); return true; }
            return false;
        });

        // navigation menu
        BottomNavigationView bottom_navigation = findViewById(R.id.bottomNavigation);
        bottom_navigation.setSelectedItemId(R.id.nav_projects);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_account) {
                    Intent intent1 = new Intent(ProjectHomeActivity.this, AccountActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_tasks) {
                    Intent intent2 = new Intent(ProjectHomeActivity.this, TasksActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_polls) {
                    Intent intent3 = new Intent(ProjectHomeActivity.this, VotingActivity.class);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_calendar) {
                    Intent intent4 = new Intent(ProjectHomeActivity.this, AgendaActivity.class);
                    startActivity(intent4);
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });

    }

    // === POPUP FOR ADD PROJECT ===
    private void showAddProjectPopup(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_add_project, null);

        EditText editProjectName = popupView.findViewById(R.id.editProjectName);
        TextView btnCreate = popupView.findViewById(R.id.btnCreateProject);
        TextView btnCancel = popupView.findViewById(R.id.btnCancelProject);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        btnCreate.setOnClickListener(v -> {
            String projectName = editProjectName.getText().toString().trim();

            if (!projectName.isEmpty()) {
                projectList.add(0, projectName);
                adapter.notifyItemInserted(0);
                Toast.makeText(this, "Created: " + projectName, Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            } else {
                editProjectName.setError("Please enter a project name");
            }
        });

        btnCancel.setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

    // === POPUP FOR SORTING FILTER ===
    private void showFilterPopup(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_filter, null);

        TextView btnSortAZ = popupView.findViewById(R.id.btnSortAZ);
        TextView btnSortZA = popupView.findViewById(R.id.btnSortZA);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        btnSortAZ.setOnClickListener(v -> {
            projectList.sort(String::compareToIgnoreCase);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Sorted A–Z", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        btnSortZA.setOnClickListener(v -> {
            projectList.sort((a, b) -> b.compareToIgnoreCase(a));
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Sorted Z–A", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView, 0, 10, Gravity.NO_GRAVITY);
    }

    // === POPUP FOR DROPDOWN PROJECT SELECTOR ===
    private void showProjectDropdown(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_project_dropdown, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                (int) getResources().getDimension(R.dimen.dropdown_popup_width),
                RecyclerView.LayoutParams.WRAP_CONTENT,
                true
        );

        RecyclerView dropdownRecycler = popupView.findViewById(R.id.recyclerDropdownProjects);
        dropdownRecycler.setLayoutManager(new LinearLayoutManager(this));

        final ProjectDropdownAdapter[] dropdownAdapter = new ProjectDropdownAdapter[1];
        dropdownAdapter[0] = new ProjectDropdownAdapter(
                this,
                projectList,
                new ProjectDropdownAdapter.OnProjectActionListener() {
                    @Override
                    public void onProjectSelected(String projectName) {
                        tvSelectedProject.setText(projectName);
                        popupWindow.dismiss();
                    }

                    @Override
                    public void onDeleteRequested(String projectName) {
                        new AlertDialog.Builder(ProjectHomeActivity.this)
                                .setTitle("Delete Project")
                                .setMessage("Delete \"" + projectName + "\"?")
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("Delete", (d, which) -> {
                                    int idx = projectList.indexOf(projectName);
                                    if (idx != -1) {
                                        projectList.remove(idx);
                                        adapter.notifyItemRemoved(idx);
                                        dropdownAdapter[0].notifyDataSetChanged();
                                        if (projectName.equals(tvSelectedProject.getText().toString())) {
                                            tvSelectedProject.setText("No Project Selected");
                                        }
                                        Toast.makeText(ProjectHomeActivity.this,
                                                "Deleted: " + projectName, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                    }
                });

        dropdownRecycler.setAdapter(dropdownAdapter[0]);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(anchorView, 0, 8, Gravity.NO_GRAVITY);




    }
}
