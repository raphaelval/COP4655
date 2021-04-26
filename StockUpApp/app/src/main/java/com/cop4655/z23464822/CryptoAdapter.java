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

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.MyViewAdapter> {

    Context context;

    public static ArrayList<CryptoModal> cryptoModalArrayList;

    private CryptoAdapter.OnCryptoListener mOnCryptoListener;

    public CryptoAdapter(Context ct, ArrayList<CryptoModal> cryptoModalArrayList, CryptoAdapter.OnCryptoListener onCryptoListener){
        context = ct;
        this.cryptoModalArrayList = cryptoModalArrayList;
        this.mOnCryptoListener = onCryptoListener;
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
        return new CryptoAdapter.MyViewAdapter(view, mOnCryptoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.MyViewAdapter holder, int position) {
        CryptoModal modal = cryptoModalArrayList.get(position);
        holder.cryptoSymView.setText(modal.getDisplaySymbol());
        holder.cryptoSymDescView.setText(modal.getStockDescription());
        if (modal.getFavStatus() == 0){
            holder.favBtnView.setBackgroundResource(R.drawable.ic_action_fav_gray);
        } else {
            holder.favBtnView.setBackgroundResource(R.drawable.ic_action_fav_yellow);
        }
    }

    @Override
    public int getItemCount() {

        return cryptoModalArrayList.size();
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cryptoSymView, cryptoSymDescView;
        ImageView favBtnView;

        OnCryptoListener onCryptoListener;

        public MyViewAdapter(@NonNull View itemView, OnCryptoListener onCryptoListener) {
            super(itemView);
            cryptoSymView = itemView.findViewById(R.id.cryptoSymbolText);
            cryptoSymDescView = itemView.findViewById(R.id.cryptoDescText);
            favBtnView = itemView.findViewById(R.id.favBtn);
            this.onCryptoListener = onCryptoListener;

            favBtnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    CryptoModal cryptoModal = cryptoModalArrayList.get(position);

                    FirebaseUser user = MainActivity.mAuth.getCurrentUser();
                    String userID = user.getUid();

                    if (cryptoModal.getFavStatus() == 0){
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

        @Override
        public void onClick(View view) {
            onCryptoListener.onCryptoClick(view, getAdapterPosition());
        }
    }

    public interface OnCryptoListener{
        void onCryptoClick(View v, int position);
    }

}