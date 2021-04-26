package com.cop4655.z23464822;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity {

    TextView nameView, emailView, totalFavView, totalStocksView, totalCryptoView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    MyFunc navFunc = new MyFunc();

    public static int favCount = 0;

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

                        break;
                    case R.id.fav:
                        navFunc.goToFav(ProfileActivity.this);
                        break;
                    case R.id.news:
                        navFunc.goToNews(ProfileActivity.this);
                        break;
                    case R.id.stocks:
                        navFunc.goToStocks(ProfileActivity.this);
                        break;
                    case R.id.crypto:
                        navFunc.goToCrypto(ProfileActivity.this);
                        break;
                    case R.id.logout:
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
        totalFavView = findViewById(R.id.totalFavView);
        totalStocksView = findViewById(R.id.totalStocksView);
        totalCryptoView = findViewById(R.id.totalCryptoView);

        GoogleSignInAccount userAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (userAccount != null) {
            nameView.setText(userAccount.getDisplayName());
            emailView.setText(userAccount.getEmail());
        }
        favCount = MainActivity.stocksCount + MainActivity.cryptoCount;
        totalFavView.setText(String.valueOf(favCount));
        totalStocksView.setText(String.valueOf(MainActivity.stocksCount));
        totalCryptoView.setText(String.valueOf(MainActivity.cryptoCount));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


}