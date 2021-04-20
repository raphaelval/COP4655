package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewAdapter> {

    Context context;
    String symbol[];
    String symbolDesc[];


    public StockAdapter(Context ct, String sym[], String symDesc[]){
        context = ct;
        symbol = sym;
        symbolDesc = symDesc;
    }

    @NonNull
    @Override
    public StockAdapter.MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.stock_row, parent, false);
        return new StockAdapter.MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.MyViewAdapter holder, int position) {
        holder.symView.setText(symbol[position]);
        holder.symDescView.setText(symbolDesc[position]);
    }

    @Override
    public int getItemCount() {
        return symbol.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {

        TextView symView, symDescView;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            symView = itemView.findViewById(R.id.symbolText);
            symDescView = itemView.findViewById(R.id.descText);
        }
    }

}
