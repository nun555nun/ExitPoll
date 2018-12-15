package com.example.exitpoll.model;

public class VoteItem {
    public final long _id;
    public final int number;
    public final String image;
    public VoteItem(long id, int number, String image) {
        this._id = id;
        this.number = number;
        this.image = image;
    }
}
