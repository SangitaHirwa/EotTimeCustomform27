package com.eot_app.nav_menu.drag_and_drop_functinality.model;

public class MapItemModel {

    private int coordinateX;
    private int coordinateY;
    private String name;
    private String description;
    private String photoOfItem;
    private String itemID;
    private String availability;

    public MapItemModel(int coordinateX, int coordinateY, String name, String description, String photoOfItem, String itemID, String availability) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.name = name;
        this.description = description;
        this.photoOfItem = photoOfItem;
        this.itemID = itemID;
        this.availability = availability;
    }

    public int getCordinetX() {
        return coordinateX;
    }

    public void setCordinetX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoOfItem() {
        return photoOfItem;
    }

    public void setPhotoOfItem(String photoOfItem) {
        this.photoOfItem = photoOfItem;
    }

    public String getItemId() {
        return itemID;
    }

    public void setItemId(String itemID) {
        this.itemID = itemID;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getCordinetY() {
        return coordinateY;
    }

    public void setCordinetY(int coordinateY) {
        this.coordinateY = coordinateY;
    }
}
