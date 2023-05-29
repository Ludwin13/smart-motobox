package com.example.smartmotobox;

public class Item {

    String id;
    String Status;
    String date;
    String time;

    public Item() {}

    public Item(String id, String Status, String date, String time) {
        this.id = id;
        this.Status = Status;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
