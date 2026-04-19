package com.example.thirdpractice.models;

public class Car {
    private int id;
    private String model;
    private String brand;
    private int year;
    private String type; // Sedan, SUV, Coupe etc
    private int imageResId;
    private String description;

    public Car(int id, String model, String brand, int year, String type, int imageResId, String description) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.type = type;
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}
