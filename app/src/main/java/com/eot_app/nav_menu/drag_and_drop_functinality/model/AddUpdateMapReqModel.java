package com.eot_app.nav_menu.drag_and_drop_functinality.model;

import java.util.List;

public class AddUpdateMapReqModel {
    private String jobId;
    private String mapId;
    private String mapLength;
    private String mapWidth;
    private String title;
    List<MapItemModel> mapItems;

    private String mapImageUrl;

    public AddUpdateMapReqModel(String jobId, String mapId, String mapLength, String mapWidth, String title, List<MapItemModel> mapItems, String mapImageUrl) {
        this.jobId = jobId;
        this.mapId = mapId;
        this.mapLength = mapLength;
        this.mapWidth = mapWidth;
        this.title = title;
        this.mapItems = mapItems;
        this.mapImageUrl = mapImageUrl;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MapItemModel> getMapItems() {
        return mapItems;
    }

    public void setMapItems(List<MapItemModel> mapItems) {
        this.mapItems = mapItems;
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
    }
}
