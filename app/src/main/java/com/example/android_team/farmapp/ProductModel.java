package com.example.android_team.farmapp;

public class ProductModel {
    private String name_ar;
    private int price;

    public ProductModel() {
        //Required Empty constructor
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
