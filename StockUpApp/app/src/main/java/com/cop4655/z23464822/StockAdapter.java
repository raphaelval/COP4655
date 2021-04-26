package com.cop4655.z23464822;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewAdapter>{

    public static ArrayList<StockModal> stockModalArrayList;
    Context context;

    private OnStockListener mOnStockListener;

    public StockAdapter(Context ct, ArrayList<StockModal> stockModalArrayList, OnStockListener onStockListener){
        context = ct;
        this.stockModalArrayList = stockModalArrayList;
        this.mOnStockListener = onStockListener;
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
        return new StockAdapter.MyViewAdapter(view, mOnStockListener);
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

    public class MyViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView symView, symDescView;
        ImageView favBtnView;

        OnStockListener onStockListener;

        public MyViewAdapter(@NonNull View itemView, OnStockListener onStockListener) {
            super(itemView);
            symView = itemView.findViewById(R.id.symbolText);
            symDescView = itemView.findViewById(R.id.descText);
            favBtnView = itemView.findViewById(R.id.favBtn);
            this.onStockListener = onStockListener;

            favBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    StockModal stockModal = stockModalArrayList.get(position);

                    FirebaseUser user = MainActivity.mAuth.getCurrentUser();
                    String userID = user.getUid();

                    if (stockModal.getFavStatus() == 0) {
                        stockModal.setFavStatus(1);
                        MainActivity.stocksCount++;
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
                        MainActivity.myRef.child(userID).child("favorites").child(stockModal.getStockName()).setValue("true");
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        stockModal.setFavStatus(0);
                        MainActivity.stocksCount--;
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
                        MainActivity.myRef.child(userID).child("favorites").child(stockModal.getStockName()).removeValue();
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStockListener.onStockClick(view, getAdapterPosition());
        }
    }

    public interface OnStockListener{
        void onStockClick(View v, int position);
    }

}
