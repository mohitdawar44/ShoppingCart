package com.shopit.shopit.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.shopit.shopit.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 2/10/18.
 */

public class GenericRecyclerViewAdapter<T,V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V>{

    private List<T> items = new ArrayList<T>();
    private ViewHolderCreater viewHolderCreater;
    private ViewHolderBinder viewHolderBinder;

    public GenericRecyclerViewAdapter(final ViewHolderCreater<V> viewHolderCreater,
                                      final ViewHolderBinder<T,V> viewHolderBinder){
        this.viewHolderCreater = viewHolderCreater;
        this.viewHolderBinder = viewHolderBinder;
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return (V)viewHolderCreater.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        viewHolderBinder.onBindViewHolder(holder,items,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onUpdate(List<T> updates){
        items.clear();
        items.addAll(updates);
        notifyDataSetChanged();
    }

    public void onUpdateSingleItem(T item){
        int index = items.indexOf(item);
        Log.e("Element found at", Integer.toString(index));
        if(index>=0){
            boolean itemRemoved = items.remove(item);
            items.add(index,item);
            Log.e("Item removed ",Boolean.toString(itemRemoved));
        }else{
            items.add(item);
        }
        notifyDataSetChanged();
    }

    public T getLastItem(){
        return items.get(getItemCount()-1);
    }
}

interface ViewHolderCreater<V extends RecyclerView.ViewHolder>{
    V onCreateViewHolder(ViewGroup parent,int viewType);
}

interface ViewHolderBinder<T,V extends RecyclerView.ViewHolder>{
    void onBindViewHolder(V viewHolder,List<T> items,int position);
}
