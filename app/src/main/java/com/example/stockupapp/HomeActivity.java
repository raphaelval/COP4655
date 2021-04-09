package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    TextView nameView, emailView;
    Button logoutBtn;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dl = (DrawerLayout)findViewById(R.id.activity_home);
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
                        Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        navFunc.goToProfile(HomeActivity.this);
                        break;
                    case R.id.fav:
                        Toast.makeText(HomeActivity.this, "Favorites",Toast.LENGTH_SHORT).show();
                        navFunc.goToFav(HomeActivity.this);
                        break;
                    case R.id.news:
                        Toast.makeText(HomeActivity.this, "News",Toast.LENGTH_SHORT).show();
                        navFunc.goToNews(HomeActivity.this);
                        break;
                    case R.id.stocks:
                        Toast.makeText(HomeActivity.this, "Stocks", Toast.LENGTH_SHORT).show();
                        navFunc.goToStocks(HomeActivity.this);
                        break;
                    case R.id.crypto:
                        Toast.makeText(HomeActivity.this, "Cryptocurrency",Toast.LENGTH_SHORT).show();
                        navFunc.goToCrypto(HomeActivity.this);
                        break;
                    case R.id.search:
                        Toast.makeText(HomeActivity.this, "Search",Toast.LENGTH_SHORT).show();
                        navFunc.goToSearch(HomeActivity.this);
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);

        GoogleSignInAccount userAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userAccount != null) {
            nameView.setText(userAccount.getDisplayName());
            emailView.setText(userAccount.getEmail());
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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