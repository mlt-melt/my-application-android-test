package com.example.thirdpractice.models;

public class Product {
    private int id;
    private String name;
    private String description;
    private int imageResId;

    public Product(int id, String name, String description, int imageResId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
