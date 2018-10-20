package com.shopit.shopit.model;

import java.util.Objects;

/**
 * Created by mohit on 30/9/18.
 */

public class Item {

    private String id;
    private String url;
    private Double price;
    private String name;
    private boolean isAvailable;
    private String key;

    public Item(){

    }

    public Item(String id,String name,Double price,String url,boolean isAvailable){
        this.id = id;
        this.url = url;
        this.price = price;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }
    public Double getPrice() {
        return price;
    }
    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return  true;

        Item otherItem = (Item) obj;

        return this.id!=null && this.id.equals(otherItem.getId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
