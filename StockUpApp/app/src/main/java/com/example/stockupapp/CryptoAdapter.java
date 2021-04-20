package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.MyViewAdapter> {

    Context context;
    //String cryptoSymbol[];
    //String cryptoSymDesc[];
    ArrayList<CryptoModal> cryptoModalArrayList;


    public CryptoAdapter(Context ct, ArrayList<CryptoModal> cryptoModalArrayList){
        context = ct;
        this.cryptoModalArrayList = cryptoModalArrayList;
        //cryptoSymbol = cryptoSym;
        //cryptoSymDesc = crypSymDesc;
    }

    public void filterList(ArrayList<CryptoModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        cryptoModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CryptoAdapter.MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.crypto_row, parent, false);
        return new CryptoAdapter.MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.MyViewAdapter holder, int position) {
        CryptoModal modal = cryptoModalArrayList.get(position);
        holder.cryptoSymView.setText(modal.getStockName());
        holder.cryptoSymDescView.setText(modal.getStockDescription());
    }

    @Override
    public int getItemCount() {

        return cryptoModalArrayList.size();
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {

        TextView cryptoSymView, cryptoSymDescView;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            cryptoSymView = itemView.findViewById(R.id.cryptoSymbolText);
            cryptoSymDescView = itemView.findViewById(R.id.cryptoDescText);
        }
    }

}