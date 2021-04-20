package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    public static String resultSymbol [];
    public static String resultDesc [];

    String url;
    String qSearch;
    String FH_API_KEY = "c1o84gq37fkqrr9sbte0";
    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);

        searchInput = findViewById(R.id.searchInput);

        dl = (DrawerLayout)findViewById(R.id.activity_search);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SearchAdapter recyclerAdapter = new SearchAdapter(this, resultSymbol, resultDesc);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.isChecked())
                    item.setCheckable(true).setChecked(false);
                else
                    item.setCheckable(true).setChecked(true);

                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile:
                        Toast.makeText(SearchActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navFunc.goToProfile(SearchActivity.this);
                        break;
                    case R.id.fav:
                        Toast.makeText(SearchActivity.this, "Favorites",Toast.LENGTH_SHORT).show();
                        navFunc.goToFav(SearchActivity.this);
                        break;
                    case R.id.news:
                        Toast.makeText(SearchActivity.this, "News",Toast.LENGTH_SHORT).show();
                        navFunc.goToNews(SearchActivity.this);
                        break;
                    case R.id.stocks:
                        Toast.makeText(SearchActivity.this, "Stocks", Toast.LENGTH_SHORT).show();
                        navFunc.goToStocks(SearchActivity.this);
                        break;
                    case R.id.crypto:
                        Toast.makeText(SearchActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();
                        navFunc.goToCrypto(SearchActivity.this);
                        break;
                    case R.id.search:
                        Toast.makeText(SearchActivity.this, "Search",Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        return true;
                }


                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void onSearchClick(){
        qSearch = searchInput.getText().toString();
        url = "https://finnhub.io/api/v1/search?q=" + qSearch + "&token=" + FH_API_KEY;
        RequestQueue searchQueue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //int count = jsonObject.getInt("count");
                            //resultSymbol = new String[count];
                            //resultDesc = new String[count];
                            JSONArray results = jsonObject.getJSONArray("result");
                            resultSymbol = new String[results.length()];
                            resultDesc = new String[results.length()];
                            for (int i=0;i<results.length();i++) {
                                JSONObject resultItem = results.getJSONObject(i);
                                resultSymbol[i] = resultItem.getString("displaySymbol");
                                resultDesc[i] = resultItem.getString("description");
                            }


                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        searchQueue.add(searchRequest);

    }
}