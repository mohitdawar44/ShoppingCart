package com.shopit.shopit.fragments;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shopit.shopit.R;
import com.shopit.shopit.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 30/9/18.
 */

public class DisplayItemRecyclerViewAdapter extends RecyclerView.Adapter<DisplayItemRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> items = new ArrayList<Item>();


    public DisplayItemRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemPrice;
        public TextView itemName;
        public ImageView itemImage;
        public CardView itemCardView;

        public ViewHolder(View v) {
            super(v);
            itemImage = v.findViewById(R.id.item_image);
            itemPrice = v.findViewById(R.id.item_price);
            itemName = v.findViewById(R.id.item_name);
            itemCardView = v.findViewById(R.id.item_card_view);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_cardlayout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemPrice.setText("$"+items.get(position).getPrice().toString());
        holder.itemName.setText(items.get(position).getName().toString());
        Glide.with(context)
                .load(items.get(position).getUrl())
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onUpdate(List<Item> updates){
        items.clear();
        items.addAll(updates);
        notifyDataSetChanged();
    }
}
