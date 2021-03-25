package com.example.compoundweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_results:
                        Toast.makeText(HistoryActivity.this, "Results", Toast.LENGTH_SHORT).show();
                        resultsPage();
                        break;
                    case R.id.action_map:
                        Toast.makeText(HistoryActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        mapsPage();
                        break;
                    case R.id.action_history:
                        Toast.makeText(HistoryActivity.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void mapsPage() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void resultsPage() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

}