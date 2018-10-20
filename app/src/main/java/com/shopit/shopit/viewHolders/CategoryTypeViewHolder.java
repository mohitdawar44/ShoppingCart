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

public class CategoryTypeViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryTypeName;
    public ImageView categoryTypeImage;
    public CardView cardView;

    public CategoryTypeViewHolder(View v) {
        super(v);
        categoryTypeImage = v.findViewById(R.id.category_type_image);
        categoryTypeName= v.findViewById(R.id.category_type_name);
        cardView = v.findViewById(R.id.category_type_card_view);
    }
}