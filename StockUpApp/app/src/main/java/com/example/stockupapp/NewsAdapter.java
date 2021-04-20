package com.example.stockupapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewAdapter> {

    Context context;
    String headline[];
    String source[];
    String summary[];
    String newsUrl[];


    public NewsAdapter(Context ct, String head[], String src[], String sum[], String nUrl[]){
        context = ct;
        headline = head;
        source = src;
        summary = sum;
        newsUrl = nUrl;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.news_row, parent, false);
        return new MyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        holder.headView.setText(headline[position]);
        holder.srcView.setText(source[position]);
        holder.sumView.setText(summary[position]);
        holder.nUrlView.setText(newsUrl[position]);
    }

    @Override
    public int getItemCount() {
        return headline.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {

        TextView headView, srcView, sumView, nUrlView;

        public MyViewAdapter(@NonNull View itemView) {
            super(itemView);
            headView = itemView.findViewById(R.id.headlineText);
            srcView = itemView.findViewById(R.id.sourceText);
            sumView = itemView.findViewById(R.id.summaryText);
            nUrlView = itemView.findViewById(R.id.urlText);
        }
    }
}
