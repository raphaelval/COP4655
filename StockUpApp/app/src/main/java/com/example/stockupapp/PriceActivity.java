package com.example.stockupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;

public class PriceActivity extends AppCompatActivity {

    int position;
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
        populate();
        /*Intent intent = getIntent();
        symbol = intent.getStringExtra("symbol");
        position = intent.getIntExtra("position", 0);

        if (StockAdapter.stockModalArrayList.get(position).getStockName().equals(symbol)){
            symbolName = StockAdapter.stockModalArrayList.get(position).getStockDescription();
            url = "https://finnhub.io/api/v1/quote?symbol="+symbol+"&token=c1o84gq37fkqrr9sbte0";
            getStockData();
        }*/

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

        symbolView.setText(StockActivity.symbol);
        titleView.setText(StockActivity.symbolName);
        priceView.setText(StockActivity.currentPrice);
        priceView.setTextColor(Color.parseColor(StockActivity.difColor));
        difPriceView.setText(StockActivity.difPrice);
        difPriceView.setTextColor(Color.parseColor(StockActivity.difColor));
        timeView.setText(StockActivity.stockTime);
        highPriceView.setText(StockActivity.highPrice);
        lowPriceView.setText(StockActivity.lowPrice);

    }

    /*public void getStockData() {
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
    }*/


}