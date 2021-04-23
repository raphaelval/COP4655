package com.example.stockupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {

    TextView nameView, emailView;

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

                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

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
                    case R.id.logout:
                        Toast.makeText(ProfileActivity.this, "Sign Out",Toast.LENGTH_SHORT).show();
                        navFunc.logout(ProfileActivity.this);
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });

        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);

        GoogleSignInAccount userAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userAccount != null) {
            nameView.setText(userAccount.getDisplayName());
            emailView.setText(userAccount.getEmail());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}