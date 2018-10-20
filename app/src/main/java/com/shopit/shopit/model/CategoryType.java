package com.shopit.shopit.model;

/**
 * Created by mohit on 4/10/18.
 */

public class CategoryType {

    private String name;
    private String url;


    public CategoryType(){}

    CategoryType(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
