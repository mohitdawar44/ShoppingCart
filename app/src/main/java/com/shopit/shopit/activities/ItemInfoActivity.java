package com.shopit.shopit.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shopit.shopit.R;

/**
 * Created by mohit on 14/10/18.
 */

public class ItemInfoActivity extends AppCompatActivity {

    private ImageView itemInfoImage;
    private TextView itemInfoName;
    private TextView itemInfoPrice;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        itemInfoImage = findViewById(R.id.item_info_image);
        itemInfoName = findViewById(R.id.item_info_name);
        itemInfoPrice = findViewById(R.id.item_info_price);
        floatingActionButton = findViewById(R.id.add_to_cart);
        final String itemImageUrl =  getIntent().getStringExtra("itemUrl");
        final String itemName = getIntent().getStringExtra("itemName");
        final Double itemPrice = getIntent().getDoubleExtra("itemPrice",0.0d);

        itemInfoName.setText(itemName);
        itemInfoPrice.setText(Double.toString(itemPrice));
        Glide.with(this)
                .load(itemImageUrl)
                .into(itemInfoImage);

    }
}
