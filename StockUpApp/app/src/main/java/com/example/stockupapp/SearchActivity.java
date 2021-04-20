package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class SearchActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dl = (DrawerLayout)findViewById(R.id.activity_search);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}