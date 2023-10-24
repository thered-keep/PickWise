package com.example.unipath1;

public class Student {
    private String name;



    private String uni;
    private String degree;
    private String nationality;
    private String img;
    private String address;
    private int id ,age;

    public Student(String name, String uni, String degree, String nationality, int age, String img, int id , String address) {
        this.name = name;
        this.uni = uni;
        this.degree = degree;
        this.nationality = nationality;
        this.age = age;
        this.img = img;
        this.id = id;
        this.address=address;
    }


    public Student() {
        this.name = "default";
        this.uni = "default";
        this.degree = "default";
        this.nationality = "default";
        this.age = 0;
        this.img = "default";
        this.id = 0;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getUni() {
        return uni;
    }

    public String getDegree() {
        return degree;
    }

    public String getNationality() {
        return nationality;
    }

    public String getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }
}
