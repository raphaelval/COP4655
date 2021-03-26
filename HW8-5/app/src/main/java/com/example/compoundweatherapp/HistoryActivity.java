package com.example.compoundweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {
    String[] dateArray = new String[5];
    String[] tempArray = new String[5];
    String[] minArray = new String[5];
    String[] maxArray = new String[5];
    String[] descArray = new String[5];
    String[] windSArray = new String[5];
    String[] windDArray = new String[5];
    String[] humidArray = new String[5];
    String WEATHER_API_KEY = "6938d49123ca790b7478ad0bb5f8d100";
    long dateTime = Long.parseLong(MainActivity.time);
    ListView listView;
    String histUrl;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        System.out.println("getting history data");
        long histDate = dateTime;

        for (i=0;i<5;i++) {
            histDate -= 86400; //get date of one day back
            getHistoryWeather(i, histDate);
        }
        System.out.println("success");

        CustomListAdapter histWeather = new CustomListAdapter(this, dateArray, tempArray, minArray, maxArray,
                descArray, windSArray, windDArray, humidArray);


        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(histWeather);

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
    public void getHistoryWeather(int i, long histDate) {

            String newHistDate = String.valueOf(histDate); //set history date as string for api call

            //format history date
            Date date = new Date(Long.valueOf(histDate * 1000L));
            SimpleDateFormat myDate = new SimpleDateFormat("EEE, MMM d");
            String strHistDate = myDate.format(date);
            dateArray[i] = strHistDate;

            histUrl = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + MainActivity.lat + "&lon=" +
                    MainActivity.lon + "&dt=" + newHistDate + "&appid=" + WEATHER_API_KEY;

            RequestQueue histQueue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest histStringRequest = new StringRequest(Request.Method.GET, histUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            // textView.setText("Response is: " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject main = jsonObject.getJSONObject("current");
                                tempArray[i] = "Temperature: " + String.valueOf(Math.round(KtoF(Double.parseDouble(main.getString("temp"))))) + " F";

                                long sunrise = Long.parseLong(main.getString("sunrise"));
                                Date sunTime = new Date(Long.valueOf(sunrise * 1000L));
                                SimpleDateFormat sunTimeForm = new SimpleDateFormat("h:mm a");
                                String strSunTime = sunTimeForm.format(sunTime);
                                minArray[i] = "Sunrise: " + strSunTime;

                                long sunset = Long.parseLong(main.getString("sunset"));
                                Date setTime = new Date(Long.valueOf(sunset * 1000L));
                                SimpleDateFormat setTimeForm = new SimpleDateFormat("h:mm a");
                                String strSetTime = setTimeForm.format(setTime);
                                maxArray[i] = "Sunset: " + strSetTime;

                                humidArray[i] = "Humidity: " + main.getString("humidity") + "%";
                                JSONArray weather = main.getJSONArray("weather");
                                JSONObject weatherDesc = weather.getJSONObject(0);
                                descArray[i] = capitalize(weatherDesc.getString("description"));
                                windSArray[i] = "Wind Speed: " + String.valueOf(Math.round(toMPH(Double.parseDouble(main.getString("wind_speed"))))) + " MPH ";
                                windDArray[i] = "Wind Direction: " + toDir(Integer.parseInt(main.getString("wind_deg")));
                            } catch (JSONException err) {
                                Log.d("Error", err.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error){
                                //Toast.makeText(view.getContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
                                System.out.println("error FATAL");
                            }
                    });
            // Add the request to the RequestQueue.
            histQueue.add(histStringRequest);
        }

    public void mapsPage() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void resultsPage() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
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
}