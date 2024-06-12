package com.example.unipath1;

public class Subject {
    private String name;
    private int semesterId;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, int semesterId) {
        this.name = name;
        this.semesterId = semesterId;
    }

    public String getName() {
        return name;
    }

    public int getSemesterId() {
        return semesterId;
    }
}
