package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class FavoritesActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        dl = (DrawerLayout)findViewById(R.id.activity_favorites);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    case R.id.search:
                        Toast.makeText(FavoritesActivity.this, "Search",Toast.LENGTH_SHORT).show();
                        navFunc.goToSearch(FavoritesActivity.this);
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
}