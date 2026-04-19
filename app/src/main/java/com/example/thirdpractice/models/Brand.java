package com.example.thirdpractice.models;

public class Brand {
    private int id;
    private String name;
    private String country;
    private int iconResId;

    public Brand(int id, String name, String country, int iconResId) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.iconResId = iconResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getIconResId() {
        return iconResId;
    }
}
