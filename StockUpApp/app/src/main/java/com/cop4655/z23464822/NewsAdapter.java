package com.cop4655.z23464822;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewAdapter> {

    Context context;
    String headline[];
    String source[];
    String summary[];
    String newsUrl[];
    String imageUrl[];

    private OnNewsListener mOnNewsListener;

    public NewsAdapter(Context ct, String head[], String src[], String sum[], String nUrl[], String imgUrl[], OnNewsListener onNewsListener){
        context = ct;
        headline = head;
        source = src;
        summary = sum;
        newsUrl = nUrl;
        imageUrl = imgUrl;
        this.mOnNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.news_row, parent, false);
        return new MyViewAdapter(view, mOnNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewAdapter holder, int position) {
        holder.headView.setText(headline[position]);
        holder.srcView.setText(source[position]);
        holder.sumView.setText(summary[position]);
        Picasso.get().load(MainActivity.imageUrl[position]).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return headline.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView headView, srcView, sumView;
        ImageView imageView;

        OnNewsListener onNewsListener;

        public MyViewAdapter(@NonNull View itemView, OnNewsListener onNewsListener) {
            super(itemView);
            headView = itemView.findViewById(R.id.headlineText);
            srcView = itemView.findViewById(R.id.sourceText);
            sumView = itemView.findViewById(R.id.summaryText);
            imageView = itemView.findViewById(R.id.imageView);
            this.onNewsListener = onNewsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNewsListener.onNewsClick(view, getAdapterPosition());
        }
    }
    public interface OnNewsListener{
        void onNewsClick(View v, int position);
    }
}
