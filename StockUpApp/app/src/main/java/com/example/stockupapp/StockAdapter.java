package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewAdapter>{

    ArrayList<StockModal> stockModalArrayList;
    Context context;


    public StockAdapter(Context ct, ArrayList<StockModal> stockModalArrayList){
        context = ct;
        this.stockModalArrayList = stockModalArrayList;
    }

    public void filterList(ArrayList<StockModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        stockModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
        StockModal modal = stockModalArrayList.get(position);
        holder.symView.setText(modal.getStockName());
        holder.symDescView.setText(modal.getStockDescription());
    }

    @Override
    public int getItemCount() {
        return stockModalArrayList.size();
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
