package com.ets.android_team.farmApp;

public class ProductModel {

    private String name_ar;

    private float price;

    private String img_url;

    private boolean availability;

    public ProductModel() {
        //Required Empty constructor
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean available) {
        availability = available;
    }
}
