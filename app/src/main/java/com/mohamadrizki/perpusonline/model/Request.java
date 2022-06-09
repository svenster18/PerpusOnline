package com.mohamadrizki.perpusonline.model;

public class Request {
    private String id;
    private Book book;
    private User requester;
    private User receiver;
    private float latitude;
    private float longitude;

    public Request(String id, Book book, User requester, User receiver, float latitude, float longitude) {
        this.id = id;
        this.book = book;
        this.requester = requester;
        this.receiver = receiver;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
