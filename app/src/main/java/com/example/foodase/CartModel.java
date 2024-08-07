package com.example.foodase;

public class CartModel {

    private String name;
    private String price;
    private String surl;

    public CartModel() {
        // Default constructor is required by Firebase
    }

    public CartModel(String name, String price, String surl) {
        this.name = name;
        this.price = price;
        this.surl = surl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}

