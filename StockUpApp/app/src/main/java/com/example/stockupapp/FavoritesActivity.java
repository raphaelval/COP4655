package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavAdapter recyclerAdapter = new FavAdapter(this, MainActivity.stockModalArrayList, MainActivity.cryptoModalArrayList);

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

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
}