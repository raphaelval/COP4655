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

public class FavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int VIEW_TYPE_STOCKS = 0;
    final int VIEW_TYPE_CRYPTO = 1;

    public static ArrayList<StockModal> stockModalArrayList;
    public static ArrayList<CryptoModal> cryptoModalArrayList;
    Context context;

    private OnStockListener mOnStockListener;
    private OnCryptoListener mOnCryptoListener;

    public FavAdapter(Context ct, ArrayList<StockModal> stockModalArrayList, ArrayList<CryptoModal> cryptoModalArrayList, OnStockListener onStockListener, OnCryptoListener onCryptoListener){
        context = ct;
        this.stockModalArrayList = stockModalArrayList;
        this.cryptoModalArrayList = cryptoModalArrayList;
        this.mOnStockListener = onStockListener;
        this.mOnCryptoListener = onCryptoListener;
    }

    public void filterList(ArrayList<StockModal> stockFilterlist, ArrayList<CryptoModal> cryptoFilterlist) {
        // below line is to add our filtered
        // list in our course array list.
        stockModalArrayList = stockFilterlist;
        cryptoModalArrayList = cryptoFilterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.fav_row, parent, false);
        return new FavAdapter.MyViewAdapter(view);*/
        if(viewType == VIEW_TYPE_STOCKS){
            LayoutInflater Inflater = LayoutInflater.from(context);
            View view = Inflater.inflate(R.layout.fav_row, parent, false);
            return new StockViewHolder(view, mOnStockListener);
        }
        if(viewType == VIEW_TYPE_CRYPTO){
            LayoutInflater Inflater = LayoutInflater.from(context);
            View view = Inflater.inflate(R.layout.fav_row, parent, false);
            return new CryptoViewHolder(view, mOnCryptoListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*StockModal modal = stockModalArrayList.get(position);
        holder.symView.setText(modal.getStockName());
        holder.symDescView.setText(modal.getStockDescription());
        if (modal.getFavStatus() == 0) {
            holder.favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
        } else {
            holder.favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
        }*/
        if (holder instanceof StockViewHolder){
            ((StockViewHolder) holder).populate(stockModalArrayList.get(position));
        }
        if (holder instanceof CryptoViewHolder){
            ((CryptoViewHolder) holder).populate(cryptoModalArrayList.get(position - stockModalArrayList.size()));
        }
    }

    @Override
    public int getItemCount() {
        return stockModalArrayList.size() + cryptoModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position){
        if(position < stockModalArrayList.size()){
            return VIEW_TYPE_STOCKS;
        }

        if(position - stockModalArrayList.size() < cryptoModalArrayList.size()){
            return VIEW_TYPE_CRYPTO;
        }

        return -1;
    }

    class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView symView, symDescView;
        ImageView favBtnView;

        OnStockListener onStockListener;

        public StockViewHolder(@NonNull View itemView, OnStockListener onStockListener) {
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
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
                        MainActivity.myRef.child(userID).child("favorites").child(stockModal.getStockName()).setValue("true");
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        stockModal.setFavStatus(0);
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
                        MainActivity.myRef.child(userID).child("favorites").child(stockModal.getStockName()).removeValue();
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(this);
        }
        public void populate (StockModal stockModal){
            symView.setText(stockModal.getStockName());
            symDescView.setText(stockModal.getStockDescription());
            if (stockModal.getFavStatus() == 0) {
                favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
            } else {
                favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
            }
        }

        @Override
        public void onClick(View view) {
            onStockListener.onStockClick(view, getAdapterPosition());
        }
    }

    public interface OnStockListener{
        void onStockClick(View v, int position);
    }

    public class CryptoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView symView, symDescView;
        ImageView favBtnView;

        OnCryptoListener onCryptoListener;

        public CryptoViewHolder(@NonNull View itemView, OnCryptoListener onCryptoListener) {
            super(itemView);
            symView = itemView.findViewById(R.id.symbolText);
            symDescView = itemView.findViewById(R.id.descText);
            favBtnView = itemView.findViewById(R.id.favBtn);
            this.onCryptoListener = onCryptoListener;

            favBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    CryptoModal cryptoModal = cryptoModalArrayList.get(position - stockModalArrayList.size());

                    FirebaseUser user = MainActivity.mAuth.getCurrentUser();
                    String userID = user.getUid();

                    if (cryptoModal.getFavStatus() == 0) {
                        cryptoModal.setFavStatus(1);
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
                        MainActivity.myRef.child(userID).child("favorites").child(cryptoModal.getStockName()).setValue("true");
                        Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        cryptoModal.setFavStatus(0);
                        favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
                        MainActivity.myRef.child(userID).child("favorites").child(cryptoModal.getStockName()).removeValue();
                        Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            itemView.setOnClickListener(this);
        }
        public void populate (CryptoModal cryptoModal){
            symView.setText(cryptoModal.getDisplaySymbol());
            symDescView.setText(cryptoModal.getStockDescription());
            if (cryptoModal.getFavStatus() == 0) {
                favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
            } else {
                favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
            }
        }

        @Override
        public void onClick(View view) {
            onCryptoListener.onCryptoClick(view, getAdapterPosition());
        }
    }
    public interface OnCryptoListener{
        void onCryptoClick(View v, int position);
    }
}
