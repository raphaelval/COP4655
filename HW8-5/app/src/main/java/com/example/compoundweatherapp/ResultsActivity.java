package com.example.compoundweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {
    TextToSpeech t1;
    TextView tempView;
    TextView feelslikeView;
    TextView humidityView;
    TextView latlonView;
    TextView countryView;
    TextView descView;
    TextView minView;
    TextView maxView;
    TextView windView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        tempView = (TextView) findViewById(R.id.tempView);
        feelslikeView = (TextView) findViewById(R.id.feelslikeView);
        humidityView = (TextView) findViewById(R.id.humidityView);
        latlonView = (TextView) findViewById(R.id.latlonView);
        countryView = (TextView) findViewById(R.id.countryView);
        descView = (TextView) findViewById(R.id.descView);
        minView = (TextView) findViewById(R.id.minView);
        maxView = (TextView) findViewById(R.id.maxView);
        windView = (TextView) findViewById(R.id.windView);

        displayResults();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_results:
                        Toast.makeText(ResultsActivity.this, "Results", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_map:
                        Toast.makeText(ResultsActivity.this, "Map", Toast.LENGTH_SHORT).show();
                        mapsPage();
                        break;
                    case R.id.action_history:
                        Toast.makeText(ResultsActivity.this, "History", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void displayResults() {
        countryView.setText("Weather for " + MainActivity.citystate + ", " + MainActivity.country);
        tempView.setText("Temperature: " + String.valueOf(Math.round(KtoF(Double.parseDouble(MainActivity.temp)))) + " F");
        feelslikeView.setText("Feels like: " + String.valueOf(Math.round(KtoF(Double.parseDouble(MainActivity.feelslike)))) + " F");
        humidityView.setText("Humidity: " + MainActivity.humidity + "%");
        latlonView.setText("Lat: " + MainActivity.lat + ", Lon: " + MainActivity.lon);
        minView.setText("Min: " + String.valueOf(Math.round(KtoF(Double.parseDouble(MainActivity.min)))) + " F");
        maxView.setText("Max: " + String.valueOf(Math.round(KtoF(Double.parseDouble(MainActivity.max)))) + " F");
        windView.setText("Wind: " + String.valueOf(Math.round(toMPH(Double.parseDouble(MainActivity.windSpeed)))) + " MPH " + toDir(Integer.parseInt(MainActivity.windDir)));
        descView.setText(capitalize(MainActivity.description));

    }
    public void goBack(View view) {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
    public static double KtoF(Double k){
        return ((k - 273.15) * 9/5 + 32);
    }
    public static double toMPH (Double k){

        return (k * 2.237);
    }
    public static String toDir (int element) {
        String direction = null;
        if ((element >= 0 && element < 23) || (element <= 360 && element >= 337)) {
            direction = "N";
        }
        else if (element >= 23 && element < 68) {
            direction = "NE";
        }
        else if (element >= 68 && element < 113) {
            direction = "E";
        }
        else if (element >= 113 && element < 158) {
            direction = "SE";
        }
        else if (element >= 158 && element < 203) {
            direction = "S";
        }
        else if (element >= 203 && element < 248) {
            direction = "SW";
        }
        else if (element >= 248 && element < 293) {
            direction = "W";
        }
        else if (element >= 293 && element < 337) {
            direction = "NW";
        }
        return direction;
    }
    public static String capitalize (String k) {
        String cap = k.substring(0, 1).toUpperCase() + k.substring(1);
        return cap;
    }
    public void mapsPage() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void onTTSClick(View v) {
        String toSpeak = countryView.getText().toString() + ", ";
        toSpeak += tempView.getText().toString() + ", ";
        toSpeak += feelslikeView.getText().toString() + ", ";
        toSpeak += minView.getText().toString() + ", ";
        toSpeak += maxView.getText().toString() + ", ";
        toSpeak += latlonView.getText().toString() + ", ";
        toSpeak += humidityView.getText().toString() + ", ";
        toSpeak += windView.getText().toString() + ", ";
        toSpeak += descView.getText().toString() + ", ";
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_LONG).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}