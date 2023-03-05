package com.spacester.checkin.model;

public class ModelRooms {
    String name, capacity, cost, id, image;
    public ModelRooms(){

    }

    public ModelRooms(String name, String capacity, String cost, String id, String image) {
        this.name = name;
        this.capacity = capacity;
        this.cost = cost;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
