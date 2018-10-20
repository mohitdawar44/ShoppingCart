package com.shopit.shopit.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shopit.shopit.R;
import com.shopit.shopit.database.EntityPersistenceListener;
import com.shopit.shopit.database.InsertEntityAsyncTask;
import com.shopit.shopit.database.InstantiateDatabase;
import com.shopit.shopit.database.PersistableItem;
import com.shopit.shopit.database.PersistableItemDao;

import java.util.ArrayList;

/**
 * Created by mohit on 14/10/18.
 */

public class ItemInfoActivity extends AppCompatActivity {

    private ImageView itemInfoImage;
    private TextView itemInfoName;
    private TextView itemInfoPrice;
    private FloatingActionButton floatingActionButton;
    private TextView size1, size2, size3, size4, size5;
    private LinearLayout color1, color2, color3, color4, color5;
    private LinearLayout size1Selection, size2Selection, size3Selection, size4Selection, size5Selection;
    private LinearLayout color1Selection, color2Selection, color3Selection, color4Selection, color5Selection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        initViews();
    }


    private void initViews() {
        itemInfoImage = findViewById(R.id.item_info_image);
        itemInfoName = findViewById(R.id.item_info_name);
        itemInfoPrice = findViewById(R.id.item_info_price);
        floatingActionButton = findViewById(R.id.add_to_cart);
        final ArrayList<TextView> sizeViews = new ArrayList<TextView>();
        final ArrayList<LinearLayout> sizeSelection = new ArrayList<LinearLayout>();
        final ArrayList<LinearLayout> colorViews = new ArrayList<LinearLayout>();
        final ArrayList<LinearLayout> colorSelection = new ArrayList<LinearLayout>();

        initSizeView(sizeViews, sizeSelection);
        initColorView(colorViews,colorSelection);

        final String itemImageUrl = getIntent().getStringExtra("itemUrl");
        final String itemName = getIntent().getStringExtra("itemName");
        final Double itemPrice = getIntent().getDoubleExtra("itemPrice", 0.0d);
        final String itemId = getIntent().getStringExtra("itemId");
        final ArrayList<String> sizes = getIntent().getStringArrayListExtra("sizes");
        final ArrayList<String> colors = getIntent().getStringArrayListExtra("colors");
        itemInfoName.setText(itemName);
        itemInfoPrice.setText(Double.toString(itemPrice));

        for (int i = 0; i < sizes.size(); i++) {
            sizeViews.get(i).setText(sizes.get(i));
            sizeSelection.get(i).setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < colors.size(); i++) {
            colorViews.get(i).setBackgroundColor(Color.parseColor(colors.get(i)));
            colorSelection.get(i).setVisibility(View.VISIBLE);
        }

        Glide.with(this)
                .load(itemImageUrl)
                .into(itemInfoImage);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersistableItemDao persistableItemDao = InstantiateDatabase.getDatabaseInstance
                        (getApplicationContext()).itemsDao();
                PersistableItem persistableItem = new PersistableItem();
                persistableItem.itemId = itemId;
                persistableItem.itemColor = "Green";
                persistableItem.itemSize = "L";

                new InsertEntityAsyncTask(ItemInfoActivity.this, persistableItem, new EntityPersistenceListener() {
                    @Override
                    public void onEntityPersisted() {
                        Toast.makeText(ItemInfoActivity.this, "Added to cart", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailureOccured() {
                        Toast.makeText(ItemInfoActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }, persistableItemDao).execute();
            }
        });


    }

    private void initSizeView(final ArrayList<TextView> sizeViews, final ArrayList<LinearLayout> sizeSelection) {
        size1 = findViewById(R.id.size1);
        size2 = findViewById(R.id.size2);
        size3 = findViewById(R.id.size3);
        size4 = findViewById(R.id.size4);
        size5 = findViewById(R.id.size5);
        size1Selection = findViewById(R.id.size1_selection);
        size2Selection = findViewById(R.id.size2_selection);
        size3Selection = findViewById(R.id.size3_selection);
        size4Selection = findViewById(R.id.size4_selection);
        size5Selection = findViewById(R.id.size5_selection);

        sizeViews.add(size1);
        sizeViews.add(size2);
        sizeViews.add(size3);
        sizeViews.add(size4);
        sizeViews.add(size5);

        sizeSelection.add(size1Selection);
        sizeSelection.add(size2Selection);
        sizeSelection.add(size3Selection);
        sizeSelection.add(size4Selection);
        sizeSelection.add(size5Selection);

        SizeOnClickListener sizeOnClickListener = new SizeOnClickListener();

        size1Selection.setOnClickListener(sizeOnClickListener);
        size2Selection.setOnClickListener(sizeOnClickListener);
        size3Selection.setOnClickListener(sizeOnClickListener);
        size4Selection.setOnClickListener(sizeOnClickListener);
        size5Selection.setOnClickListener(sizeOnClickListener);

        size1Selection.setVisibility(View.GONE);
        size2Selection.setVisibility(View.GONE);
        size3Selection.setVisibility(View.GONE);
        size4Selection.setVisibility(View.GONE);
        size5Selection.setVisibility(View.GONE);
    }

    private void initColorView(final ArrayList<LinearLayout> colorViews, final ArrayList<LinearLayout> colorSelection) {
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);
        color5 = findViewById(R.id.color5);
        color1Selection = findViewById(R.id.color1_selection);
        color2Selection = findViewById(R.id.color2_selection);
        color3Selection = findViewById(R.id.color3_selection);
        color4Selection = findViewById(R.id.color4_selection);
        color5Selection = findViewById(R.id.color5_selection);

        colorViews.add(color1);
        colorViews.add(color2);
        colorViews.add(color3);
        colorViews.add(color4);
        colorViews.add(color5);

        colorSelection.add(color1Selection);
        colorSelection.add(color2Selection);
        colorSelection.add(color3Selection);
        colorSelection.add(color4Selection);
        colorSelection.add(color5Selection);

        ColorOnClickListener colorOnClickListener = new ColorOnClickListener();

        color1Selection.setOnClickListener(colorOnClickListener);
        color2Selection.setOnClickListener(colorOnClickListener);
        color3Selection.setOnClickListener(colorOnClickListener);
        color4Selection.setOnClickListener(colorOnClickListener);
        color5Selection.setOnClickListener(colorOnClickListener);

        color1Selection.setVisibility(View.GONE);
        color2Selection.setVisibility(View.GONE);
        color3Selection.setVisibility(View.GONE);
        color4Selection.setVisibility(View.GONE);
        color5Selection.setVisibility(View.GONE);
    }

    class SizeOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.size1_selection:
                    size1Selection.setBackgroundResource(R.color.black);
                    if (size2Selection.getVisibility() == View.VISIBLE) {
                        size2Selection.setBackgroundResource(R.color.white);
                    }
                    if (size3Selection.getVisibility() == View.VISIBLE) {
                        size3Selection.setBackgroundResource(R.color.white);
                    }
                    if (size4Selection.getVisibility() == View.VISIBLE) {
                        size4Selection.setBackgroundResource(R.color.white);
                    }
                    if (size5Selection.getVisibility() == View.VISIBLE) {
                        size5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.size2_selection:
                    size2Selection.setBackgroundResource(R.color.black);
                    if (size1Selection.getVisibility() == View.VISIBLE) {
                        size1Selection.setBackgroundResource(R.color.white);
                    }
                    if (size3Selection.getVisibility() == View.VISIBLE) {
                        size3Selection.setBackgroundResource(R.color.white);
                    }
                    if (size4Selection.getVisibility() == View.VISIBLE) {
                        size4Selection.setBackgroundResource(R.color.white);
                    }
                    if (size5Selection.getVisibility() == View.VISIBLE) {
                        size5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.size3_selection:
                    size3Selection.setBackgroundResource(R.color.black);
                    if (size1Selection.getVisibility() == View.VISIBLE) {
                        size1Selection.setBackgroundResource(R.color.white);
                    }
                    if (size2Selection.getVisibility() == View.VISIBLE) {
                        size2Selection.setBackgroundResource(R.color.white);
                    }
                    if (size4Selection.getVisibility() == View.VISIBLE) {
                        size4Selection.setBackgroundResource(R.color.white);
                    }
                    if (size5Selection.getVisibility() == View.VISIBLE) {
                        size5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.size4_selection:
                    size4Selection.setBackgroundResource(R.color.black);
                    if (size1Selection.getVisibility() == View.VISIBLE) {
                        size1Selection.setBackgroundResource(R.color.white);
                    }
                    if (size2Selection.getVisibility() == View.VISIBLE) {
                        size2Selection.setBackgroundResource(R.color.white);
                    }
                    if (size3Selection.getVisibility() == View.VISIBLE) {
                        size3Selection.setBackgroundResource(R.color.white);
                    }
                    if (size5Selection.getVisibility() == View.VISIBLE) {
                        size5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.size5_selection:
                    size5Selection.setBackgroundResource(R.color.black);
                    if (size1Selection.getVisibility() == View.VISIBLE) {
                        size1Selection.setBackgroundResource(R.color.white);
                    }
                    if (size2Selection.getVisibility() == View.VISIBLE) {
                        size2Selection.setBackgroundResource(R.color.white);
                    }
                    if (size3Selection.getVisibility() == View.VISIBLE) {
                        size3Selection.setBackgroundResource(R.color.white);
                    }
                    if (size4Selection.getVisibility() == View.VISIBLE) {
                        size4Selection.setBackgroundResource(R.color.white);
                    }
                    break;
            }
        }
    }


    class ColorOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.color1_selection:
                    color1Selection.setBackgroundResource(R.color.black);
                    if (color2Selection.getVisibility() == View.VISIBLE) {
                        color2Selection.setBackgroundResource(R.color.white);
                    }
                    if (color3Selection.getVisibility() == View.VISIBLE) {
                        color3Selection.setBackgroundResource(R.color.white);
                    }
                    if (color4Selection.getVisibility() == View.VISIBLE) {
                        color4Selection.setBackgroundResource(R.color.white);
                    }
                    if (color5Selection.getVisibility() == View.VISIBLE) {
                        color5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.color2_selection:
                    color2Selection.setBackgroundResource(R.color.black);
                    if (color1Selection.getVisibility() == View.VISIBLE) {
                        color1Selection.setBackgroundResource(R.color.white);
                    }
                    if (color3Selection.getVisibility() == View.VISIBLE) {
                        color3Selection.setBackgroundResource(R.color.white);
                    }
                    if (color4Selection.getVisibility() == View.VISIBLE) {
                        color4Selection.setBackgroundResource(R.color.white);
                    }
                    if (color5Selection.getVisibility() == View.VISIBLE) {
                        color5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.color3_selection:
                    color3Selection.setBackgroundResource(R.color.black);
                    if (color1Selection.getVisibility() == View.VISIBLE) {
                        color1Selection.setBackgroundResource(R.color.white);
                    }
                    if (color2Selection.getVisibility() == View.VISIBLE) {
                        color2Selection.setBackgroundResource(R.color.white);
                    }
                    if (color4Selection.getVisibility() == View.VISIBLE) {
                        color4Selection.setBackgroundResource(R.color.white);
                    }
                    if (color5Selection.getVisibility() == View.VISIBLE) {
                        color5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.color4_selection:
                    color4Selection.setBackgroundResource(R.color.black);
                    if (color1Selection.getVisibility() == View.VISIBLE) {
                        color1Selection.setBackgroundResource(R.color.white);
                    }
                    if (color2Selection.getVisibility() == View.VISIBLE) {
                        color2Selection.setBackgroundResource(R.color.white);
                    }
                    if (color3Selection.getVisibility() == View.VISIBLE) {
                        color3Selection.setBackgroundResource(R.color.white);
                    }
                    if (color5Selection.getVisibility() == View.VISIBLE) {
                        color5Selection.setBackgroundResource(R.color.white);
                    }
                    break;
                case R.id.color5_selection:
                    color5Selection.setBackgroundResource(R.color.black);
                    if (color1Selection.getVisibility() == View.VISIBLE) {
                        color1Selection.setBackgroundResource(R.color.white);
                    }
                    if (color2Selection.getVisibility() == View.VISIBLE) {
                        color2Selection.setBackgroundResource(R.color.white);
                    }
                    if (color3Selection.getVisibility() == View.VISIBLE) {
                        color3Selection.setBackgroundResource(R.color.white);
                    }
                    if (color4Selection.getVisibility() == View.VISIBLE) {
                        size4Selection.setBackgroundResource(R.color.white);
                    }
                    break;
            }
        }
    }
}
