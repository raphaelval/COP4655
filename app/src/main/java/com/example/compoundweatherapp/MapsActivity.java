package com.example.compoundweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_results:
                        Toast.makeText(MapsActivity.this, "Results", Toast.LENGTH_SHORT).show();
                        resultsPage();
                        break;
                    case R.id.action_map:
                        Toast.makeText(MapsActivity.this, "Map", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.action_history:
                        Toast.makeText(MapsActivity.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void resultsPage() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
}
//END OF PROGRAM