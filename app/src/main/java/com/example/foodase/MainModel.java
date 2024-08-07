package com.example.foodase;

public class MainModel {

    String name, price, surl;

    MainModel()
    {

    }


    public MainModel(String name, String price, String surl) {
        this.name = name;
        this.price = price;
        this.surl = surl;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}