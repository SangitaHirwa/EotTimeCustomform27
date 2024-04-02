package com.eot_app.nav_menu.drag_and_drop_functinality.model;

import java.util.List;

public class DragAndDropMapModel {
    private  String map_id;
    private  String map_jobid;
    private  String map_jsonData;
    private  String mapLength;
    private  String mapWidth;
    private  String floorID;
    private  String mapImageUrl;
    private  String mapType;
    private  String title;
    private  String fieldLocation;
    private  String status;
    private  String color;
    private  String mapImgUrl;
    private List<MapItemModel> mapItems;

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getMap_jobid() {
        return map_jobid;
    }

    public void setMap_jobid(String map_jobid) {
        this.map_jobid = map_jobid;
    }

    public String getMap_jsonData() {
        return map_jsonData;
    }

    public void setMap_jsonData(String map_jsonData) {
        this.map_jsonData = map_jsonData;
    }

    public String getMapLength() {
        return mapLength;
    }

    public void setMapLength(String mapLength) {
        this.mapLength = mapLength;
    }

    public String getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(String mapWidth) {
        this.mapWidth = mapWidth;
    }

    public String getFloorID() {
        return floorID;
    }

    public void setFloorID(String floorID) {
        this.floorID = floorID;
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFieldLocation() {
        return fieldLocation;
    }

    public void setFieldLocation(String fieldLocation) {
        this.fieldLocation = fieldLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMapImgUrl() {
        return mapImgUrl;
    }

    public void setMapImgUrl(String mapImgUrl) {
        this.mapImgUrl = mapImgUrl;
    }

    public List<MapItemModel> getMapItems() {
        return mapItems;
    }

    public void setMapItems(List<MapItemModel> mapItems) {
        this.mapItems = mapItems;
    }
}
