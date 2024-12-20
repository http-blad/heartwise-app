package com.example.heartwise;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HomeActivityMain extends AppCompatActivity {

    private Button measureHeartRateButton;
    private Button viewResultsButton;
    private BPMResultDao bpmResultDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home as the selected default item
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"bpm-result-db").allowMainThreadQueries().build();

        bpmResultDao = db.bpmDao();

        TextView currentBPM = findViewById(R.id.heartRateTextView);

        List<BPMResult> bpmResults = bpmResultDao.getAll();

        if(!bpmResults.isEmpty()) {
            BPMResult lastIndex = bpmResults.get(bpmResults.size() - 1);
            if(lastIndex != null) {
                currentBPM.setText(lastIndex.getResult());
            }
        }

        // Set the listener for navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                // Navigate to the Home screen
                startActivity(new Intent(getApplicationContext(), HomeActivityMain.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_result) {
                // Navigate to the Result screen
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_measure) {
                // Navigate to the Measure Heart Rate screen
                startActivity(new Intent(getApplicationContext(), CameraMonitor.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_activity_history) {
                // Navigate to the Activity History screen
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_settings) {
                // Navigate to the Settings screen
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false; // Return false if none of the conditions matched
        });

        // Initialize buttons
        measureHeartRateButton = findViewById(R.id.measureHeartRateButton);
        viewResultsButton = findViewById(R.id.viewResultsButton);

        // Set onClick listener for Measure Heart Rate button
        measureHeartRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Measure Heart Rate Activity
                Intent intent = new Intent(HomeActivityMain.this, CameraMonitor.class);
                startActivity(intent);
            }
        });

        // Set onClick listener for View History button
        viewResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to History Activity
                Intent intent = new Intent(HomeActivityMain.this, ResultActivity.class);
                startActivity(intent);
            }
        });
    }
}
