package com.example.sprintshare;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TasksActivity extends AppCompatActivity implements TaskAdapter.OnTaskUpdateListener {

    private HashMap<String, List<TaskItem>> tabTasks;
    private List<String> tabNames;
    private String currentTab;

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TextView tvCurrentTab;
    private ProgressBar progressBar;
    private TextView tvProgressPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // views
        recyclerView = findViewById(R.id.recyclerTasks);
        tvCurrentTab = findViewById(R.id.tvCurrentTab);
        progressBar = findViewById(R.id.progressBar);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);

        // init data
        tabTasks = new HashMap<>();
        tabNames = new ArrayList<>();
//        currentTab = "Milestone";
        tabNames.add(currentTab);
        tabTasks.put(currentTab, new ArrayList<>());

        // recycler
        adapter = new TaskAdapter(this, tabTasks.get(currentTab), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        tvCurrentTab.setText(currentTab);
        updateProgress();

        // FAB add: open choice dialog (Add Task or Create Tab)
        findViewById(R.id.btnAddTask).setOnClickListener(v -> showAddChoiceDialog());

        // icon dropdown: switch/manage tabs
        findViewById(R.id.btnDropdown).setOnClickListener(v -> showSwitchTabsDialog());

        // filter button: show sorting popup
        findViewById(R.id.btnFilter).setOnClickListener(v -> showFilterPopup(v));

        // navigation menu
        BottomNavigationView bottom_navigation = findViewById(R.id.bottomNavigation);
        bottom_navigation.setSelectedItemId(R.id.nav_tasks);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_account) {
                    Intent intent1 = new Intent(TasksActivity.this, AccountActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_projects) {
                    Intent intent2 = new Intent(TasksActivity.this, ProjectHomeActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_polls) {
                    Intent intent3 = new Intent(TasksActivity.this, VotingActivity.class);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_calendar) {
                    Intent intent4 = new Intent(TasksActivity.this,AgendaActivity.class);
                    startActivity(intent4);
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });

    }

    // show first dialog: choose add task OR create tab
    private void showAddChoiceDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Action");
        b.setItems(new CharSequence[]{"Add Task to Current Milestone", "Add New Milestone"}, (dialog, which) -> {
            if (which == 0) showAddTaskDialog();
            else showCreateTabDialog();
        });
        b.show();
    }

    // add task dialog
    private void showAddTaskDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_add_task, null);
        EditText etName = view.findViewById(R.id.editTaskName);
        EditText etDesc = view.findViewById(R.id.editTaskDescription);
        EditText etAssignee = view.findViewById(R.id.editAssignee);
        EditText etDeadline = view.findViewById(R.id.editDeadline);
        TextView btnAdd = view.findViewById(R.id.btnAddTask);
        TextView btnCancel = view.findViewById(R.id.btnCancelTask);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String assignee = etAssignee.getText().toString().trim();
            String deadline = etDeadline.getText().toString().trim();
            if (name.isEmpty() || assignee.isEmpty() || deadline.isEmpty()) {
                // simple validation
                if (name.isEmpty()) etName.setError("Required");
                if (assignee.isEmpty()) etAssignee.setError("Required");
                if (deadline.isEmpty()) etDeadline.setError("Required");
                return;
            }
            TaskItem t = new TaskItem(name, desc, assignee, deadline);
            tabTasks.get(currentTab).add(t);
            adapter.notifyItemInserted(tabTasks.get(currentTab).size() - 1);
            updateProgress();
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // create a new tab dialog
    private void showCreateTabDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_create_task_tab, null);
        EditText etName = view.findViewById(R.id.editTaskTabName);
        TextView btnCreate = view.findViewById(R.id.btnCreateTab);
        TextView btnCancel = view.findViewById(R.id.btnCancelTab);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        btnCreate.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) { etName.setError("Required"); return; }
            if (tabTasks.containsKey(name)) { etName.setError("Tab exists"); return; }

            tabTasks.put(name, new ArrayList<>());
            tabNames.add(name);

            // switch to new tab
            currentTab = name;
            tvCurrentTab.setText(currentTab);
            adapter.updateTasks(tabTasks.get(currentTab));
            updateProgress();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // switch/manage tabs dialog (custom ListView with delete buttons)
    private void showSwitchTabsDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_switch_task_tab, null);
        ListView list = view.findViewById(R.id.listTaskTabs);
        TextView btnClose = view.findViewById(R.id.btnCloseTabList);

        // Declare adapter first
        final TaskTabListAdapter[] listAdapter = new TaskTabListAdapter[1];

        // Listener for selecting or deleting tabs
        TaskTabListAdapter.Listener listener = new TaskTabListAdapter.Listener() {
            @Override
            public void onTabSelected(String tabName) {
                currentTab = tabName;
                tvCurrentTab.setText(tabName);
                adapter.updateTasks(tabTasks.get(tabName));
                updateProgress();
                dialogRef.dismiss();
            }

            @Override
            public void onDeleteRequested(String tabName) {
                // confirm delete (no deletion if only one tab)
                if (tabNames.size() == 1) {
                    // can't delete last tab
                    return;
                }
                new AlertDialog.Builder(TasksActivity.this)
                        .setTitle("Delete tab")
                        .setMessage("Delete \"" + tabName + "\"? This will remove its tasks.")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Delete", (d, w) -> {
                            int idx = tabNames.indexOf(tabName);
                            tabNames.remove(tabName);
                            tabTasks.remove(tabName);
                            if (tabName.equals(currentTab)) {
                                // switch to first available
                                currentTab = tabNames.get(0);
                                tvCurrentTab.setText(currentTab);
                                adapter.updateTasks(tabTasks.get(currentTab));
                            }
                            updateProgress();
                            listAdapter[0].notifyDataSetChanged();
                        }).show();
            }
        };

        // Adapter for ListView
        listAdapter[0] = new TaskTabListAdapter(this, tabNames, listener);
        list.setAdapter(listAdapter[0]);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        dialogRef = dialog; // keep reference
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showFilterPopup(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_filter, null);

        TextView btnSortAZ = popupView.findViewById(R.id.btnSortAZ);
        TextView btnSortZA = popupView.findViewById(R.id.btnSortZA);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        btnSortAZ.setOnClickListener(v -> {
            List<TaskItem> sortedList = new ArrayList<>(tabTasks.get(currentTab));
            sortedList.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            adapter.updateTasks(sortedList);
            tabTasks.put(currentTab, sortedList); // optional: update the map
            Toast.makeText(this, "Sorted A–Z", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        btnSortZA.setOnClickListener(v -> {
            List<TaskItem> sortedList = new ArrayList<>(tabTasks.get(currentTab));
            sortedList.sort((a, b) -> b.getName().compareToIgnoreCase(a.getName()));
            adapter.updateTasks(sortedList);
            tabTasks.put(currentTab, sortedList); // optional: update the map
            Toast.makeText(this, "Sorted Z–A", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        popupWindow.showAsDropDown(anchorView, 0, 10, Gravity.NO_GRAVITY);
    }




    // helper refs used above (keeps code compact)
    private AlertDialog dialogRef;
    private TaskTabListAdapter listAdapterRef;

    // progress update method
    private void updateProgress() {
        List<TaskItem> list = tabTasks.get(currentTab);
        if (list == null || list.isEmpty()) {
            progressBar.setProgress(0);
            tvProgressPercent.setText("0%");
            return;
        }
        int done = 0;
        for (TaskItem t : list) if (t.isCompleted()) done++;
        int pct = (int) ((done / (float) list.size()) * 100);
        progressBar.setProgress(pct);
        tvProgressPercent.setText(pct + "%");
    }

    // adapter callback
    @Override
    public void onTaskCompletedChanged() {
        updateProgress();
    }
}
