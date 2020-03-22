package com.springsecurity.app.model;

public class Student {
    private final Integer id;
    private final String name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "{" + " id='" + id + "'" + ", name='" + name + "'" + "}";
    }
}