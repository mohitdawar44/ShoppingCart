package com.shopit.shopit.model;

/**
 * Created by mohit on 30/9/18.
 */

public class Item {

    private String url;
    private Double price;
    private String name;

    public Item(){

    }

    public Item(String name,Double price,String url){
        this.url = url;
        this.price = price;
        this.name = name;
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
}
