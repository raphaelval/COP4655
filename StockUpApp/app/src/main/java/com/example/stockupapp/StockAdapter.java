package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        if (modal.getFavStatus() == 0){
            holder.favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
        } else {
            holder.favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
        }
    }

    @Override
    public int getItemCount() {
        return stockModalArrayList.size();
    }
    public class MyViewAdapter extends RecyclerView.ViewHolder {

        TextView symView, symDescView;
        ImageView favBtnView;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            symView = itemView.findViewById(R.id.symbolText);
            symDescView = itemView.findViewById(R.id.descText);
            favBtnView = itemView.findViewById(R.id.favBtn);

            favBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    StockModal stockModal = stockModalArrayList.get(position);

                    if (stockModal.getFavStatus() == 0) {
                        stockModal.setFavStatus(1);
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        stockModal.setFavStatus(0);
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
