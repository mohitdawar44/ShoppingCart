package com.shopit.shopit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shopit.shopit.R;
import com.shopit.shopit.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 30/9/18.
 */

public class DisplayItemsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DisplayItemRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.item_recyclerview, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("shopit").child("categories").child("clothes").child("jackets");
        valueEventListener = new ItemValueEventListener();
        mAdapter = new DisplayItemRecyclerViewAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(valueEventListener!=null)
        databaseReference.removeEventListener(valueEventListener);
    }

    class ItemValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Item> updates = new ArrayList<>();
            for(DataSnapshot itemSnapshot:dataSnapshot.getChildren()){
                updates.add(itemSnapshot.getValue(Item.class));
            }
            mAdapter.onUpdate(updates);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
