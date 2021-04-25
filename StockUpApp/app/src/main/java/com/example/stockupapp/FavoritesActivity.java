package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FavoritesActivity extends AppCompatActivity implements FavAdapter.OnStockListener,FavAdapter.OnCryptoListener  {

    RecyclerView recyclerView;
    FavAdapter recyclerAdapter = new FavAdapter(this, MainActivity.stockModalArrayList, MainActivity.cryptoModalArrayList, this::onStockClick, this::onCryptoClick);

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DecimalFormat cdf = new DecimalFormat("0.0000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recyclerView);

        dl = (DrawerLayout)findViewById(R.id.activity_favorites);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filter();

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile:
                        Toast.makeText(FavoritesActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navFunc.goToProfile(FavoritesActivity.this);

                        break;
                    case R.id.fav:
                        Toast.makeText(FavoritesActivity.this, "Favorites",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.news:
                        Toast.makeText(FavoritesActivity.this, "News",Toast.LENGTH_SHORT).show();
                        navFunc.goToNews(FavoritesActivity.this);
                        break;
                    case R.id.stocks:
                        Toast.makeText(FavoritesActivity.this, "Stocks", Toast.LENGTH_SHORT).show();
                        navFunc.goToStocks(FavoritesActivity.this);
                        break;
                    case R.id.crypto:
                        Toast.makeText(FavoritesActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();
                        navFunc.goToCrypto(FavoritesActivity.this);
                        break;
                    case R.id.logout:
                        Toast.makeText(FavoritesActivity.this, "Sign Out",Toast.LENGTH_SHORT).show();
                        navFunc.logout(FavoritesActivity.this);
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });
    }

    private void filter() {
        // creating a new array list to filter our data.
        ArrayList<StockModal> stockFilteredlist = new ArrayList<>();
        ArrayList<CryptoModal> cryptoFilteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (StockModal item : MainActivity.stockModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getFavStatus() == 1) {
                // if the item is matched we are
                // adding it to our filtered list.
                stockFilteredlist.add(item);
            }
        }
        for (CryptoModal item : MainActivity.cryptoModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getFavStatus() == 1) {
                // if the item is matched we are
                // adding it to our filtered list.
                cryptoFilteredlist.add(item);
            }
        }

        if (stockFilteredlist.isEmpty() && cryptoFilteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            recyclerAdapter.filterList(stockFilteredlist, cryptoFilteredlist);
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recyclerAdapter.filterList(stockFilteredlist, cryptoFilteredlist);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStockClick(View v, int position) {
        StockModal stockModal = FavAdapter.stockModalArrayList.get(position);
        StockActivity.symbol = stockModal.getStockName();
        StockActivity.symbolName = FavAdapter.stockModalArrayList.get(position).getStockDescription();
        String url = "https://finnhub.io/api/v1/quote?symbol="+StockActivity.symbol+"&token=c1o84gq37fkqrr9sbte0";
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest newsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            StockActivity.currentPrice = jsonObject.getString("c");
                            StockActivity.highPrice = jsonObject.getString("h");
                            StockActivity.lowPrice = jsonObject.getString("l");
                            StockActivity.stockTime = jsonObject.getString("t");
                            String openPrice = jsonObject.getString("o");
                            float o = Float.parseFloat(openPrice);
                            float c = Float.parseFloat(StockActivity.currentPrice);
                            float dif = c - o;
                            if (dif < 0){
                                StockActivity.difPrice = "DOWN $" + df.format(Math.abs(dif));
                                StockActivity.difColor = "#E00000";
                            } else {
                                StockActivity.difPrice = "UP $" + df.format(dif);
                                StockActivity.difColor = "#0FB800";
                            }
                            float fCurrentPrice = Float.parseFloat(StockActivity.currentPrice);
                            float fHighPrice = Float.parseFloat(StockActivity.highPrice);
                            float fLowPrice = Float.parseFloat(StockActivity.lowPrice);
                            StockActivity.currentPrice = "$" + df.format(fCurrentPrice);
                            StockActivity.highPrice = "$" + df.format(fHighPrice);
                            StockActivity.lowPrice = "$" + df.format(fLowPrice);
                            long dateInMil = Long.parseLong(StockActivity.stockTime);
                            Date date = new Date(Long.valueOf(dateInMil*1000L));
                            SimpleDateFormat myDate = new SimpleDateFormat("EEE, MMM d, h:mm a");
                            StockActivity.stockTime = myDate.format(date);
                            PriceActivity.type = 0;


                        } catch (JSONException err) {
                            Log.d("Error", err.toString());
                        }
                        Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavoritesActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(newsRequest);
    }

    @Override
    public void onCryptoClick(View v, int position) {
        CryptoModal cryptoModal = FavAdapter.cryptoModalArrayList.get(position - FavAdapter.stockModalArrayList.size());
        CryptoActivity.symbol = cryptoModal.getDisplaySymbol();
        String fromCurr = CryptoActivity.symbol.substring(0, CryptoActivity.symbol.lastIndexOf("/"));
        String toCurr = CryptoActivity.symbol.substring(CryptoActivity.symbol.lastIndexOf("/")+1);
        CryptoActivity.symbolName = FavAdapter.cryptoModalArrayList.get(position - FavAdapter.stockModalArrayList.size()).getStockDescription();
        String url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCurr + "&to_currency=" + toCurr + "&apikey=E9BO6GFXKGXN0757";
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest newsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject exchange = jsonObject.getJSONObject("Realtime Currency Exchange Rate");
                            CryptoActivity.currentPrice = exchange.getString("5. Exchange Rate");
                            CryptoActivity.highPrice = exchange.getString("2. From_Currency Name");
                            CryptoActivity.lowPrice = exchange.getString("4. To_Currency Name");
                            CryptoActivity.stockTime = exchange.getString("6. Last Refreshed");
                            CryptoActivity.boldTop = "FROM";
                            CryptoActivity.boldBottom = "TO";
                            PriceActivity.type = 1;
                            float fCurrentPrice = Float.parseFloat(CryptoActivity.currentPrice);
                            CryptoActivity.currentPrice = cdf.format(fCurrentPrice) + " " + toCurr;
                            /*long dateInMil = Long.parseLong(stockTime);
                            Date date = new Date(Long.valueOf(dateInMil*1000L));
                            SimpleDateFormat myDate = new SimpleDateFormat("EEE, MMM d, h:mm a");
                            stockTime = myDate.format(date);*/


                        } catch (JSONException err) {
                            Log.d("Error", err.toString());
                            CryptoActivity.highPrice = "NO INFO ON THIS EXCHANGE RATE";
                            CryptoActivity.currentPrice = "N/A";
                            CryptoActivity.stockTime = "-";
                            CryptoActivity.lowPrice = "";
                            CryptoActivity.boldBottom = "";
                            CryptoActivity.boldTop = "";
                            PriceActivity.type = 1;
                        }
                        Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavoritesActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(newsRequest);


    }
}