package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.MyViewAdapter> {

    Context context;
    String cryptoSymbol[];
    String cryptoSymDesc[];


    public CryptoAdapter(Context ct, String cryptoSym[], String crypSymDesc[]){
        context = ct;
        cryptoSymbol = cryptoSym;
        cryptoSymDesc = crypSymDesc;
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
        holder.cryptoSymView.setText(cryptoSymbol[position]);
        holder.cryptoSymDescView.setText(cryptoSymDesc[position]);
    }

    @Override
    public int getItemCount() {
        return cryptoSymbol.length;
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