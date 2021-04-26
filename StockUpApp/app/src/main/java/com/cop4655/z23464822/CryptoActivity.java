package com.cop4655.z23464822;

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

public class CryptoActivity extends AppCompatActivity implements CryptoAdapter.OnCryptoListener {

    RecyclerView recyclerView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    private static DecimalFormat df = new DecimalFormat("0.0000");

    public static String symbol;
    public static String symbolName;
    public static String currentPrice;
    public static String highPrice;
    public static String lowPrice;
    public static String stockTime;
    public static String difPrice;
    public static String boldTop;
    public static String boldBottom;

    public static String url;

    CryptoAdapter recyclerAdapter = new CryptoAdapter(this, MainActivity.cryptoModalArrayList, this::onCryptoClick);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);

        MainActivity.mAuth = FirebaseAuth.getInstance();
        MainActivity.mFirebaseDatabase = FirebaseDatabase.getInstance();
        MainActivity.myRef = MainActivity.mFirebaseDatabase.getReference();

        recyclerView = findViewById(R.id.recyclerView);

        dl = (DrawerLayout)findViewById(R.id.activity_crypto);
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

                        navFunc.goToProfile(CryptoActivity.this);
                        break;

                    case R.id.fav:
                        navFunc.goToFav(CryptoActivity.this);
                        break;
                    case R.id.news:
                        navFunc.goToNews(CryptoActivity.this);
                        break;
                    case R.id.stocks:
                        navFunc.goToStocks(CryptoActivity.this);
                        break;
                    case R.id.crypto:

                        break;
                    case R.id.logout:
                        navFunc.logout(CryptoActivity.this);
                        break;
                    default:
                        return true;
                }


                return true;


            }
        });
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
        ArrayList<CryptoModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (CryptoModal item : MainActivity.cryptoModalArrayList) {
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

    @Override
    public void onCryptoClick(View v, int position) {
        Toast.makeText(this, "Loading info...", Toast.LENGTH_SHORT).show();
        CryptoModal cryptoModal = CryptoAdapter.cryptoModalArrayList.get(position);
        symbol = cryptoModal.getDisplaySymbol();
        String fromCurr = symbol.substring(0, symbol.lastIndexOf("/"));
        String toCurr = symbol.substring(symbol.lastIndexOf("/")+1);
        symbolName = CryptoAdapter.cryptoModalArrayList.get(position).getStockDescription();
        url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCurr + "&to_currency=" + toCurr + "&apikey=E9BO6GFXKGXN0757";
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest newsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject exchange = jsonObject.getJSONObject("Realtime Currency Exchange Rate");
                            currentPrice = exchange.getString("5. Exchange Rate");
                            highPrice = exchange.getString("2. From_Currency Name");
                            lowPrice = exchange.getString("4. To_Currency Name");
                            stockTime = exchange.getString("6. Last Refreshed");
                            boldTop = "FROM";
                            boldBottom = "TO";
                            PriceActivity.type = 1;
                            float fCurrentPrice = Float.parseFloat(currentPrice);
                            currentPrice = df.format(fCurrentPrice) + " " + toCurr;
                            /*long dateInMil = Long.parseLong(stockTime);
                            Date date = new Date(Long.valueOf(dateInMil*1000L));
                            SimpleDateFormat myDate = new SimpleDateFormat("EEE, MMM d, h:mm a");
                            stockTime = myDate.format(date);*/


                        } catch (JSONException err) {
                            Log.d("Error", err.toString());
                            highPrice = "NO INFO ON THIS EXCHANGE RATE";
                            currentPrice = "N/A";
                            stockTime = "-";
                            lowPrice = "";
                            boldBottom = "";
                            boldTop = "";
                            PriceActivity.type = 1;
                        }
                        Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CryptoActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(newsRequest);


    }
}