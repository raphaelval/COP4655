package com.example.stockupapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
