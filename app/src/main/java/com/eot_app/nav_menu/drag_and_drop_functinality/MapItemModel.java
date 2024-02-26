package com.eot_app.nav_menu.drag_and_drop_functinality;

public class MapItemModel {

    private int xCordinet;
    private int yCordinet;
    private String name;
    private String description;
    private String photoOfItem;
    private String ItemId;
    private boolean Availability;

    public MapItemModel(int xCordinet, int yCordinet, String name, String description, String photoOfItem, String itemId, boolean availability) {
        this.xCordinet = xCordinet;
        this.yCordinet = yCordinet;
        this.name = name;
        this.description = description;
        this.photoOfItem = photoOfItem;
        ItemId = itemId;
        Availability = availability;
    }

    public int getxCordinet() {
        return xCordinet;
    }

    public void setxCordinet(int xCordinet) {
        this.xCordinet = xCordinet;
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
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public boolean getAvailability() {
        return Availability;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }

    public int getyCordinet() {
        return yCordinet;
    }

    public void setyCordinet(int yCordinet) {
        this.yCordinet = yCordinet;
    }
}
