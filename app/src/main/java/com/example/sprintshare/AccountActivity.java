package com.example.sprintshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {
    TextView gotoAccountSettings;
    TextView logoutPage;

    String[] status = {"Online" , "Away" , "Busy"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        autoCompleteTextView = findViewById(R.id.auto_complete_text);
        adapterStatus = new ArrayAdapter<String>(this, R.layout.list_status, status);

        // Attach adapter
        autoCompleteTextView.setAdapter(adapterStatus);

        // Set default status (first item in the array)
        autoCompleteTextView.setText(status[0], false);  // Default = "Online"

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                Toast.makeText(AccountActivity.this, "Status: " + status, Toast.LENGTH_SHORT).show();
            }
        });

        gotoAccountSettings = findViewById(R.id.gotoAccountSettings);
        gotoAccountSettings.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, AccountSettingsActivity.class);
            startActivity(intent);
        });

        logoutPage = findViewById(R.id.logoutPage);
        logoutPage.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // navigation menu
        BottomNavigationView bottom_navigation = findViewById(R.id.bottomNavigation);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_polls) {
                    Intent intent1 = new Intent(AccountActivity.this, VotingActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_projects) {
                    Intent intent2 = new Intent(AccountActivity.this, ProjectHomeActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_calendar) {
                    Intent intent3 = new Intent(AccountActivity.this, AgendaActivity.class);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                }else if (item.getItemId()  == R.id.nav_tasks) {
                    Intent intent4 = new Intent(AccountActivity.this, TasksActivity.class);
                    startActivity(intent4);
                    overridePendingTransition(0, 0);
                }

                return true;
            }
        });

    }

}