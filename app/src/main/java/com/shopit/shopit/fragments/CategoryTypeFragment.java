package com.shopit.shopit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shopit.shopit.R;
import com.shopit.shopit.model.Category;
import com.shopit.shopit.model.CategoryType;
import com.shopit.shopit.navigation.FragmentTransactionUtils;
import com.shopit.shopit.viewHolders.CategoryTypeViewHolder;
import com.shopit.shopit.viewHolders.CategoryViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 30/9/18.
 */

public class CategoryTypeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private static AppCompatActivity appCompatActivity;
    private static int navViewId;

    public static CategoryTypeFragment getInstance(AppCompatActivity mAppCompatActivity, int mNavViewId){
        appCompatActivity = mAppCompatActivity;
        navViewId = mNavViewId;
        return new CategoryTypeFragment();
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
        final Bundle bundle = getArguments();
        List<String> childHierarchy = bundle.getStringArrayList("childHierarchy");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        for(String child:childHierarchy){
            databaseReference = databaseReference.child(child);
        }

        valueEventListener = new CategoryTypeValueEventListener();
        //mAdapter = new DisplayItemRecyclerViewAdapter(getContext());

        mAdapter = new GenericRecyclerViewAdapter<CategoryType,CategoryTypeViewHolder>(new ViewHolderCreater<CategoryTypeViewHolder>() {
            @Override
            public CategoryTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(getContext()).inflate(R.layout.category_type_cardlayout,parent,false);
                return new CategoryTypeViewHolder(v);
            }
        }, new ViewHolderBinder<CategoryType, CategoryTypeViewHolder>() {
            @Override
            public void onBindViewHolder(final CategoryTypeViewHolder holder,final List<CategoryType> categories,final int position) {
                Glide.with(getContext())
                        .load(categories.get(position).getUrl())
                        .into(holder.categoryTypeImage);
                holder.categoryTypeName.setText(categories.get(position).getName());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DisplayItemsFragment displayItemsFragment = DisplayItemsFragment.getInstance(appCompatActivity,navViewId);
                        Bundle bundle = new Bundle();
                        ArrayList<String> childHierarchy = new ArrayList<String>();
                        childHierarchy.add("shopit");
                        childHierarchy.add(categories.get(position).getName());
                        bundle.putStringArrayList("childHierarchy",childHierarchy);
                        displayItemsFragment.setArguments(bundle);
                        FragmentTransactionUtils.changeFragment(appCompatActivity,displayItemsFragment,navViewId);
                    }
                });

            }
                });
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


    class CategoryTypeValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<CategoryType> updates = new ArrayList<>();
            for(DataSnapshot itemSnapshot:dataSnapshot.getChildren()){
                updates.add(itemSnapshot.getValue(CategoryType.class));
            }
            mAdapter.onUpdate(updates);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
