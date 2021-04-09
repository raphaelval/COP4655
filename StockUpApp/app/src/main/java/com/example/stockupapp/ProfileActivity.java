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

public class ProfileActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dl = (DrawerLayout)findViewById(R.id.activity_profile);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile:
                        Toast.makeText(ProfileActivity.this, "Profile", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.fav:
                        Toast.makeText(ProfileActivity.this, "Favorites",Toast.LENGTH_SHORT).show();
                        navFunc.goToFav(ProfileActivity.this);
                        break;
                    case R.id.news:
                        Toast.makeText(ProfileActivity.this, "News",Toast.LENGTH_SHORT).show();
                        navFunc.goToNews(ProfileActivity.this);
                        break;
                    case R.id.stocks:
                        Toast.makeText(ProfileActivity.this, "Stocks", Toast.LENGTH_SHORT).show();
                        navFunc.goToStocks(ProfileActivity.this);
                        break;
                    case R.id.crypto:
                        Toast.makeText(ProfileActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();
                        navFunc.goToCrypto(ProfileActivity.this);
                        break;
                    case R.id.search:
                        Toast.makeText(ProfileActivity.this, "Search",Toast.LENGTH_SHORT).show();
                        navFunc.goToSearch(ProfileActivity.this);
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