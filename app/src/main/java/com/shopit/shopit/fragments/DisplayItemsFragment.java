package com.shopit.shopit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shopit.shopit.R;
import com.shopit.shopit.activities.ItemInfoActivity;
import com.shopit.shopit.model.Item;
import com.shopit.shopit.viewHolders.ItemViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mohit on 30/9/18.
 */

public class DisplayItemsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference databaseReference;
    private List<ValueEventListener> valueEventListeners = new ArrayList<>();
    private static final Integer slotSize = 100;

    private static AppCompatActivity appCompatActivity;
    private static int navViewId;

    public static DisplayItemsFragment getInstance(AppCompatActivity mAppCompatActivity,int mNavViewId){
        appCompatActivity = mAppCompatActivity;
        navViewId = mNavViewId;
        return new DisplayItemsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.item_recyclerview, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Bundle bundle = getArguments();
        final List<String> childHierarchy = bundle.getStringArrayList("childHierarchy");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        for(String child:childHierarchy){
            databaseReference = databaseReference.child(child);
        }
        //mAdapter = new DisplayItemRecyclerViewAdapter(getContext());

        mAdapter = new GenericRecyclerViewAdapter<Item,ItemViewHolder>(new ViewHolderCreater<ItemViewHolder>() {
            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(getContext()).inflate(R.layout.item_cardlayout,parent,false);
                return new ItemViewHolder(v);
            }
        }, new ViewHolderBinder<Item, ItemViewHolder>() {
            @Override
            public void onBindViewHolder(ItemViewHolder holder, final List<Item> items, final int position) {
                holder.itemPrice.setText("$"+items.get(position).getPrice().toString());
                holder.itemName.setText(items.get(position).getName().toString());
                Glide.with(getContext())
                        .load(items.get(position).getUrl())
                        .into(holder.itemImage);
                holder.itemCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ItemInfoActivity.class);
                        intent.putExtra("itemUrl",items.get(position).getUrl());
                        intent.putExtra("itemName",items.get(position).getName());
                        intent.putExtra("itemPrice",items.get(position).getPrice());
                        intent.putExtra("itemId",items.get(position).getId());
                        String sizesAvailable = items.get(position).getSizesAvailable();
                        String[] sizes = sizesAvailable.split(",");

                        ArrayList<String> sizeList = new ArrayList<String>();

                        for(String size:sizes)
                            sizeList.add(size);

                        String colorsAvailable = items.get(position).getColorsAvailable();
                        String[] colors = colorsAvailable.split(",");

                        ArrayList<String> colorsList = new ArrayList<String>();

                        for(String color:colors)
                            colorsList.add(color);

                        intent.putStringArrayListExtra("sizes",sizeList);
                        intent.putStringArrayListExtra("colors",colorsList);
                        getContext().startActivity(intent);
                    }
                });
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1)){
                    Item item = (Item) mAdapter.getLastItem();
                    String lastKeyReceived = item.getKey();
                    Log.e("Scrolling Reached last","Attaching new listener from "+lastKeyReceived);
                    ValueEventListener nextSlotListener = new ItemValueEventListener();
                    databaseReference.orderByKey().startAt(lastKeyReceived).limitToFirst(slotSize).addValueEventListener(nextSlotListener);
                    valueEventListeners.add(nextSlotListener);

                     }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
            ValueEventListener initialValueEventListener = new ItemValueEventListener();
            valueEventListeners.add(initialValueEventListener);
            databaseReference.orderByKey().limitToFirst(slotSize).addValueEventListener(initialValueEventListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        for(ValueEventListener listener:valueEventListeners)
        databaseReference.removeEventListener(listener);
    }

    class ItemValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot itemSnapshot:dataSnapshot.getChildren()){
                Item item = itemSnapshot.getValue(Item.class);
                item.setKey(itemSnapshot.getKey());
                mAdapter.onUpdateSingleItem(item);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
