package com.example.stockupapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MyFunc extends AppCompatActivity {

    public void goToProfile(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }
    public void goToFav(Context context) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        context.startActivity(intent);
    }
    public void goToNews(Context context) {
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
    }
    public void goToStocks(Context context) {
        Intent intent = new Intent(context, StockActivity.class);
        context.startActivity(intent);
    }
    public void goToCrypto(Context context) {
        Intent intent = new Intent(context, CryptoActivity.class);
        context.startActivity(intent);
    }
    public void goToSearch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
