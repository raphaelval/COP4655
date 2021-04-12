package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class StockActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        dl = (DrawerLayout)findViewById(R.id.activity_stock);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setCheckedItem(R.id.activity_stock);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    case R.id.search:
                        Toast.makeText(StockActivity.this, "Search",Toast.LENGTH_SHORT).show();
                        navFunc.goToSearch(StockActivity.this);
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