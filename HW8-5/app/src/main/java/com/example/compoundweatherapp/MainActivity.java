package com.example.compoundweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final int REQ_CODE = 100;
    public static String searchStr;
    public static String temp;
    public static String feelslike;
    public static String humidity;
    public static String lat;
    public static String lon;
    public static String country;
    public static String citystate;
    public static String description;
    public static String min;
    public static String max;
    public static String windSpeed;
    public static String windDir;
    public static String time;

    String WEATHER_API_KEY = "6938d49123ca790b7478ad0bb5f8d100";
    String url;
    Button resultsBtn;
    EditText searchInput;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        resultsBtn = (Button) findViewById(R.id.nextPage);
        searchInput = findViewById(R.id.searchInput);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void DisplayWeather(View view) {

        searchStr = searchInput.getText().toString();
        try{
            Integer.parseInt(searchStr);
            url = "https://api.openweathermap.org/data/2.5/weather?zip=" + searchStr + "&appid=" + WEATHER_API_KEY;
        } catch (Exception e) {
            url = "https://api.openweathermap.org/data/2.5/weather?q=" + searchStr + "&appid=" + WEATHER_API_KEY;
        }

        getWeather(view);
    }
    public void getWeather(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // textView.setText("Response is: " + response);
                        //System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject main = jsonObject.getJSONObject("main");
                            temp = main.getString("temp");
                            min = main.getString("temp_min");
                            max = main.getString("temp_max");
                            feelslike = main.getString("feels_like");
                            humidity = main.getString("humidity");
                            JSONObject coord = jsonObject.getJSONObject("coord");
                            lat = coord.getString("lat");
                            lon = coord.getString("lon");
                            JSONObject sys = jsonObject.getJSONObject("sys");
                            country = sys.getString("country");
                            citystate = jsonObject.getString("name");
                            JSONArray weather = jsonObject.getJSONArray("weather");
                            JSONObject weatherDesc = weather.getJSONObject(0);
                            description = weatherDesc.getString("description");
                            JSONObject wind = jsonObject.getJSONObject("wind");
                            windSpeed = wind.getString("speed");
                            windDir = wind.getString("deg");
                            time = jsonObject.getString("dt");
                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                        nextPage(view);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void getLocation(View view) {
        // create class object
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                    "&lon=" + longitude + "&appid=" + WEATHER_API_KEY;
            getWeather(view);

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    public void nextPage(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
    public void onSTTClick(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry your device is not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchInput.setText(result.get(0).toString());

                }
                break;
            }
        }
    }
}