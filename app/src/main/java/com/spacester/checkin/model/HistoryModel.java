package com.spacester.checkin.model;

public class HistoryModel {
    String check,id,room,time,user;

    public HistoryModel(){

    }

    public HistoryModel(String check, String id, String room, String time, String user) {
        this.check = check;
        this.id = id;
        this.room = room;
        this.time = time;
        this.user = user;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
