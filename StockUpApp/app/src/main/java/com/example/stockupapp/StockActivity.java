package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StockActivity extends AppCompatActivity implements StockAdapter.OnStockListener{

    RecyclerView recyclerView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    private static DecimalFormat df = new DecimalFormat("0.00");

    public static String symbol;
    public static String symbolName;
    public static String currentPrice;
    public static String highPrice;
    public static String lowPrice;
    public static String stockTime;
    public static String difPrice;
    public static String difColor;

    public static String url;

    StockAdapter recyclerAdapter = new StockAdapter(StockActivity.this, MainActivity.stockModalArrayList, this::onStockClick);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        MainActivity.mAuth = FirebaseAuth.getInstance();
        MainActivity.mFirebaseDatabase = FirebaseDatabase.getInstance();
        MainActivity.myRef = MainActivity.mFirebaseDatabase.getReference();

        recyclerView = findViewById(R.id.recyclerView);

        dl = (DrawerLayout)findViewById(R.id.activity_stock);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                        Toast.makeText(StockActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navFunc.goToProfile(StockActivity.this);
                        break;
                    case R.id.fav:
                        Toast.makeText(StockActivity.this, "Favorites",Toast.LENGTH_SHORT).show();
                        navFunc.goToFav(StockActivity.this);
                        break;
                    case R.id.news:
                        Toast.makeText(StockActivity.this, "News",Toast.LENGTH_SHORT).show();
                        navFunc.goToNews(StockActivity.this);
                        break;
                    case R.id.stocks:
                        Toast.makeText(StockActivity.this, "Stocks", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.crypto:
                        Toast.makeText(StockActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();
                        navFunc.goToCrypto(StockActivity.this);
                        break;
                    case R.id.logout:
                        Toast.makeText(StockActivity.this, "Sign Out",Toast.LENGTH_SHORT).show();
                        navFunc.logout(StockActivity.this);
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });
    }

    public void getStockPrice(){

    }

    @Override
    public void onStockClick(View v, int position) {
        StockModal stockModal = StockAdapter.stockModalArrayList.get(position);
        symbol = stockModal.getStockName();
        symbolName = StockAdapter.stockModalArrayList.get(position).getStockDescription();
        url = "https://finnhub.io/api/v1/quote?symbol="+symbol+"&token=c1o84gq37fkqrr9sbte0";
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
                            String openPrice = jsonObject.getString("o");
                            float o = Float.parseFloat(openPrice);
                            float c = Float.parseFloat(currentPrice);
                            float dif = c - o;
                            if (dif < 0){
                                difPrice = "DOWN $" + df.format(Math.abs(dif));
                                difColor = "#E00000";
                            } else {
                                difPrice = "UP $" + df.format(dif);
                                difColor = "#0FB800";
                            }
                            float fCurrentPrice = Float.parseFloat(currentPrice);
                            float fHighPrice = Float.parseFloat(highPrice);
                            float fLowPrice = Float.parseFloat(lowPrice);
                            currentPrice = "$" + df.format(fCurrentPrice);
                            highPrice = "$" + df.format(fHighPrice);
                            lowPrice = "$" + df.format(fLowPrice);


                        } catch (JSONException err) {
                            Log.d("Error", err.toString());
                        }
                        Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StockActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(newsRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<StockModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (StockModal item : MainActivity.stockModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if ((item.getStockName().toLowerCase().contains(text.toLowerCase())) || (item.getStockDescription().toLowerCase().contains(text.toLowerCase()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recyclerAdapter.filterList(filteredlist);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}