package com.cop4655.z23464822;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    public static FirebaseAuth mAuth;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference myRef;

    public static String headline [];
    public static String source [];
    public static String summary [];
    public static String newsUrl [];
    public static String imageUrl [];
    public static ArrayList<StockModal> stockModalArrayList;
    public static ArrayList<CryptoModal> cryptoModalArrayList;
    public static ArrayList<String> favList = new ArrayList<>();

    public static int stocksCount = 0;
    public static int cryptoCount = 0;

    String url;
    String FH_API_KEY = "c1o84gq37fkqrr9sbte0";

    String userID;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            userID = currentUser.getUid();
            myRef.child(userID).child("favorites").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()){
                        favList.add(snap.getKey());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            stocksCount = 0;
            cryptoCount = 0;
            getStockData();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        getSupportActionBar().hide();

        createRequest();

        findViewById(R.id.googleSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stocksCount = 0;
                cryptoCount = 0;
                Toast.makeText(MainActivity.this, "Signing in...", Toast.LENGTH_SHORT).show();
                signIn();
            }
        });


    }

    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();
                            myRef.child(userID).child("favorites").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    for (DataSnapshot snap : snapshot.getChildren()){
                                        favList.add(snap.getKey());
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                            stocksCount = 0;
                            cryptoCount = 0;
                            getStockData();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getStockData() {
        url = "https://finnhub.io/api/v1/news?category=general&token=" + FH_API_KEY;
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest newsRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            headline = new String[jsonObject.length()];
                            source = new String[jsonObject.length()];
                            summary = new String[jsonObject.length()];
                            newsUrl = new String[jsonObject.length()];
                            imageUrl = new String[jsonObject.length()];
                            for (int i=0;i<jsonObject.length();i++) {
                                JSONObject news = jsonObject.getJSONObject(i);
                                headline[i] = news.getString("headline");
                                source[i] = news.getString("source");
                                summary[i] = news.getString("summary");
                                newsUrl[i] = news.getString("url");
                                imageUrl[i] = news.getString("image");
                            }


                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(newsRequest);

        url = "https://finnhub.io/api/v1/stock/symbol?exchange=US&token=" + FH_API_KEY + "&currency=USD";
        // Request a string response from the provided URL.
        StringRequest stockRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            stockModalArrayList = new ArrayList<>();
                            for (int i=0;i<jsonObject.length();i++) {
                                JSONObject stocks = jsonObject.getJSONObject(i);
                                String jsonSymbol = stocks.getString("symbol");
                                String jsonDesc = stocks.getString("description");

                                //IF FAVORITES FROM DATABASE EQUALS JSONSYMBOL THEN THERE IS A MATCH
                                int match = 0;
                                for (int j = 0; j < favList.size(); j++){
                                    if (favList.get(j).equals(jsonSymbol)) {
                                        match = 1;
                                    }
                                }
                                if (match == 1){
                                    stockModalArrayList.add(new StockModal(jsonSymbol, jsonDesc, 1));
                                    stocksCount++;
                                } else {
                                    stockModalArrayList.add(new StockModal(jsonSymbol, jsonDesc, 0));
                                }
                            }

                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stockRequest);

        url = "https://finnhub.io/api/v1/crypto/symbol?exchange=binance&token=" + FH_API_KEY;
        // Request a string response from the provided URL.
        StringRequest cryptoRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            cryptoModalArrayList = new ArrayList<>();
                            for (int i=0;i<jsonObject.length();i++) {
                                JSONObject crypto = jsonObject.getJSONObject(i);
                                String jsonDisplaySymbol = crypto.getString("displaySymbol");
                                String jsonSymbol = crypto.getString("symbol");
                                String jsonDesc = crypto.getString("description");

                                //IF FAVORITES FROM DATABASE EQUALS JSONSYMBOL THEN THERE IS A MATCH
                                int match = 0;
                                for (int j = 0; j < favList.size(); j++){
                                    if (favList.get(j).equals(jsonSymbol)) {
                                        match = 1;
                                    }
                                }
                                if (match == 1){
                                    cryptoModalArrayList.add(new CryptoModal(jsonSymbol, jsonDisplaySymbol, jsonDesc, 1));
                                    cryptoCount++;
                                } else {
                                    cryptoModalArrayList.add(new CryptoModal(jsonSymbol, jsonDisplaySymbol, jsonDesc, 0));
                                }
                            }


                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(cryptoRequest);
    }

}