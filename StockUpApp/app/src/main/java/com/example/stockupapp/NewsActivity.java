package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.OnNewsListener{

    RecyclerView recyclerView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.recyclerView);

        dl = (DrawerLayout)findViewById(R.id.activity_news);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NewsAdapter recyclerAdapter = new NewsAdapter(this, MainActivity.headline, MainActivity.source,
                MainActivity.summary, MainActivity.newsUrl, MainActivity.imageUrl, this::onNewsClick);
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
                        Toast.makeText(NewsActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navFunc.goToProfile(NewsActivity.this);
                        break;
                    case R.id.fav:
                        Toast.makeText(NewsActivity.this, "Favorites",Toast.LENGTH_SHORT).show();
                        navFunc.goToFav(NewsActivity.this);
                        break;
                    case R.id.news:
                        Toast.makeText(NewsActivity.this, "News",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.stocks:
                        Toast.makeText(NewsActivity.this, "Stocks", Toast.LENGTH_SHORT).show();
                        navFunc.goToStocks(NewsActivity.this);
                        break;
                    case R.id.crypto:
                        Toast.makeText(NewsActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();
                        navFunc.goToCrypto(NewsActivity.this);
                        break;
                    case R.id.logout:
                        Toast.makeText(NewsActivity.this, "Sign Out",Toast.LENGTH_SHORT).show();
                        navFunc.logout(NewsActivity.this);
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

    @Override
    public void onNewsClick(View v, int position) {
        Uri uri = Uri.parse(MainActivity.newsUrl[position]);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}