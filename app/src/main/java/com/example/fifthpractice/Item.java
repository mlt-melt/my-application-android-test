package com.example.fifthpractice;

public class Item {
    private int id;
    private String title;
    private String description;
    private int quantity;
    private String category;
    private String note;

    public Item(int id, String title, String description, int quantity, String category, String note) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.note = note;
    }

    public Item(String title, String description, int quantity, String category, String note) {
        this(0, title, description, quantity, category, note);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
