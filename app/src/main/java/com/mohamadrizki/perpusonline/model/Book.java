package com.mohamadrizki.perpusonline.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int id;
    private String name;
    private String author;
    private String cover;
    private String synopsis;

    public Book(String name, String author, String cover, String synopsis) {
        this.name = name;
        this.author = author;
        this.cover = cover;
        this.synopsis = synopsis;
    }

    public Book(int id, String name, String author, String cover, String synopsis) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.cover = cover;
        this.synopsis = synopsis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.cover);
        dest.writeString(this.synopsis);
    }

    public Book() {

    }

    private Book(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.author = in.readString();
        this.cover = in.readString();
        this.synopsis = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
