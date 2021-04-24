package com.example.stockupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class PriceActivity extends AppCompatActivity {

    String symbol;
    String symbolName;
    String url;

    String currentPrice;
    String highPrice;
    String lowPrice;
    String stockTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        //TO GET SYMBOL FROM CLICK, ARRAYLIST.CONTAINS("SYMBOL")
        //DETERMINE IF FROM STOCKS OR CRYPTO
        //THEN USE ARRAYLIST.GET(ARRAYLIST.INDEXOF("SYMBOL"))."GET INFO..."

        symbol = "TSLA";
        symbolName = "Tesla Inc.";
        url = "https://finnhub.io/api/v1/quote?symbol="+symbol+"&token=c1o84gq37fkqrr9sbte0";
        getStockData();
    }

    public void populate(){
        TextView symbolView, titleView, priceView, difPriceView, timeView, highPriceView, lowPriceView;

        symbolView = findViewById(R.id.symbolView);
        titleView = findViewById(R.id.titleView);
        priceView = findViewById(R.id.priceView);
        difPriceView = findViewById(R.id.difPriceView);
        timeView = findViewById(R.id.timeView);
        highPriceView = findViewById(R.id.highPriceView);
        lowPriceView = findViewById(R.id.lowPriceView);

        symbolView.setText(symbol);
        titleView.setText(symbolName);
        priceView.setText(currentPrice);

        timeView.setText(stockTime);
        highPriceView.setText(highPrice);
        lowPriceView.setText(lowPrice);

    }

    public void getStockData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest newsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            currentPrice = jsonObject.getString("c");
                            highPrice = jsonObject.getString("h");
                            lowPrice = jsonObject.getString("l");
                            stockTime = jsonObject.getString("t");
                            populate();

                        } catch (JSONException err) {
                            Log.d("Error", err.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PriceActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(newsRequest);
    }


}