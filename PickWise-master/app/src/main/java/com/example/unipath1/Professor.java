package com.example.unipath1;

public class Professor {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String url_img;
    private double rating;
    private int image, rank;



    public Professor(int id, String name, String email, String phone, String url_img, Double rating) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.url_img = url_img;
        this.rating = rating;
    }



    public Professor(String name, String url_img, double rating,int rank) {
        this.name = name;
        this.url_img = url_img;
        this.rating = rating;
        this.rank=rank;
    }

    public Professor(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl_img() {
        return url_img;
    }

    public int getId() {
        return id;
    }

}
