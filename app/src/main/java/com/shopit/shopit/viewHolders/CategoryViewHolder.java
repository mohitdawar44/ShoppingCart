package com.shopit.shopit.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.shopit.shopit.R;

/**
 * Created by mohit on 3/10/18.
 */

public  class CategoryViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout categoryLinearLayout;

    public CategoryViewHolder(View v) {
        super(v);
        categoryLinearLayout = v.findViewById(R.id.category_card_background);
    }
}
