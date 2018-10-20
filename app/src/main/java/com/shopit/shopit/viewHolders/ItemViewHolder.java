package com.shopit.shopit.viewHolders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopit.shopit.R;

/**
 * Created by mohit on 3/10/18.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemPrice;
    public TextView itemName;
    public ImageView itemImage;
    public CardView itemCardView;

    public ItemViewHolder(View v) {
        super(v);
        itemImage = v.findViewById(R.id.item_image);
        itemPrice = v.findViewById(R.id.item_price);
        itemName = v.findViewById(R.id.item_name);
        itemCardView = v.findViewById(R.id.item_card_view);
    }
}