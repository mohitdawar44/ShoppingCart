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
import com.shopit.shopit.navigation.FragmentTransactionUtils;
import com.shopit.shopit.viewHolders.CategoryViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 30/9/18.
 */

public class  CategoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private static AppCompatActivity appCompatActivity;
    private static int navViewId;

    public static CategoryFragment getInstance(AppCompatActivity mAppCompatActivity,int mNavViewId){
        appCompatActivity = mAppCompatActivity;
        navViewId = mNavViewId;
        return new CategoryFragment();
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("shopit").child("category");
        valueEventListener = new CategoryValueEventListener();
        //mAdapter = new DisplayItemRecyclerViewAdapter(getContext());

        mAdapter = new GenericRecyclerViewAdapter<Category,CategoryViewHolder>(new ViewHolderCreater<CategoryViewHolder>() {
            @Override
            public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(getContext()).inflate(R.layout.category_card_layout,parent,false);
                return new CategoryViewHolder(v);
            }
        }, new ViewHolderBinder<Category, CategoryViewHolder>() {
            @Override
            public void onBindViewHolder(final CategoryViewHolder holder, final List<Category> categories, final int position) {
                Glide.with(getContext())
                        .load(categories.get(position).getUrl())
                        .into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                holder.categoryLinearLayout.setBackground(resource);
                            }
                        });
                holder.categoryLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CategoryTypeFragment categoryTypeFragment = CategoryTypeFragment.getInstance(appCompatActivity,navViewId);
                        Bundle bundle = new Bundle();
                        ArrayList<String> childHierarchy = new ArrayList<String>();
                        childHierarchy.add("shopit");
                        childHierarchy.add(categories.get(position).getName());
                        bundle.putStringArrayList("childHierarchy",childHierarchy);
                        categoryTypeFragment.setArguments(bundle);
                        FragmentTransactionUtils.changeFragment(appCompatActivity,categoryTypeFragment,navViewId);
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

    class CategoryValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Category> updates = new ArrayList<>();
            for(DataSnapshot itemSnapshot:dataSnapshot.getChildren()){
                updates.add(itemSnapshot.getValue(Category.class));
            }
            mAdapter.onUpdate(updates);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
