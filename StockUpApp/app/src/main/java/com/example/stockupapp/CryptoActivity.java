package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CryptoActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();
    CryptoAdapter recyclerAdapter = new CryptoAdapter(this, MainActivity.cryptoModalArrayList);

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

                        Toast.makeText(CryptoActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navFunc.goToProfile(CryptoActivity.this);
                        break;

                    case R.id.fav:
                        Toast.makeText(CryptoActivity.this, "Favorites",Toast.LENGTH_SHORT).show();
                        navFunc.goToFav(CryptoActivity.this);
                        break;
                    case R.id.news:
                        Toast.makeText(CryptoActivity.this, "News",Toast.LENGTH_SHORT).show();
                        navFunc.goToNews(CryptoActivity.this);
                        break;
                    case R.id.stocks:
                        Toast.makeText(CryptoActivity.this, "Stocks", Toast.LENGTH_SHORT).show();
                        navFunc.goToStocks(CryptoActivity.this);
                        break;
                    case R.id.crypto:
                        Toast.makeText(CryptoActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.logout:
                        Toast.makeText(CryptoActivity.this, "Sign Out",Toast.LENGTH_SHORT).show();
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