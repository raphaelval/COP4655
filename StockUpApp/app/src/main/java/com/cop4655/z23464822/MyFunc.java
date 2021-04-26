package com.cop4655.z23464822;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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
    public void logout(Context context) {
        MainActivity.stocksCount=0;
        MainActivity.cryptoCount=0;
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
