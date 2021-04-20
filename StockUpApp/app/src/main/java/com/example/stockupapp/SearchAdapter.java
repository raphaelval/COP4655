package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewAdapter> {

    Context context;
    String resultSymbol[];
    String resultDesc[];


    public SearchAdapter(Context ct, String sym[], String symDesc[]){
        context = ct;
        resultSymbol = sym;
        resultDesc = symDesc;
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.result_row, parent, false);
        return new SearchAdapter.MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewAdapter holder, int position) {
        holder.resultSymView.setText(resultSymbol[position]);
        holder.resultSymDescView.setText(resultDesc[position]);
    }

    @Override
    public int getItemCount() {
        try{
            return resultSymbol.length;
        } catch (Error e){
            return 0;
        }
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {

        TextView resultSymView, resultSymDescView;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            resultSymView = itemView.findViewById(R.id.resultSymbolText);
            resultSymDescView = itemView.findViewById(R.id.resultDescText);
        }
    }

}
