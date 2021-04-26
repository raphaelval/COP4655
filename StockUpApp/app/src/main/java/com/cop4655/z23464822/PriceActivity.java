package com.cop4655.z23464822;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PriceActivity extends AppCompatActivity {

    public static int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        populate();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void populate(){
        TextView symbolView, titleView, priceView, difPriceView, timeView, highPriceView, lowPriceView, boldTopView, boldBottomView;

        symbolView = findViewById(R.id.symbolView);
        titleView = findViewById(R.id.titleView);
        priceView = findViewById(R.id.priceView);
        difPriceView = findViewById(R.id.difPriceView);
        timeView = findViewById(R.id.timeView);
        highPriceView = findViewById(R.id.highPriceView);
        lowPriceView = findViewById(R.id.lowPriceView);
        boldTopView = findViewById(R.id.boldTopView);
        boldBottomView = findViewById(R.id.boldBottomView);

        if (type == 0) {
            symbolView.setText(StockActivity.symbol);
            titleView.setText(StockActivity.symbolName);
            priceView.setText(StockActivity.currentPrice);
            priceView.setTextColor(Color.parseColor(StockActivity.difColor));
            difPriceView.setText(StockActivity.difPrice);
            difPriceView.setTextColor(Color.parseColor(StockActivity.difColor));
            timeView.setText(StockActivity.stockTime);
            highPriceView.setText(StockActivity.highPrice);
            lowPriceView.setText(StockActivity.lowPrice);
        }
        if (type == 1) {
            symbolView.setText(CryptoActivity.symbol);
            titleView.setText(CryptoActivity.symbolName);
            priceView.setText(CryptoActivity.currentPrice);
            difPriceView.setText(CryptoActivity.difPrice);
            timeView.setText(CryptoActivity.stockTime);
            highPriceView.setText(CryptoActivity.highPrice);
            lowPriceView.setText(CryptoActivity.lowPrice);
            boldTopView.setText(CryptoActivity.boldTop);
            boldBottomView.setText(CryptoActivity.boldBottom);
            boldBottomView.setTextColor(getResources().getColor(R.color.design_default_color_primary));
            boldTopView.setTextColor(getResources().getColor(R.color.design_default_color_primary));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }


}