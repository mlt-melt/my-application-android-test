package com.example.sixthpractice;

public class Book {
    public long id;
    public String title;
    public String author;
    public int year;

    public Book() {
    }

    public Book(long id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
