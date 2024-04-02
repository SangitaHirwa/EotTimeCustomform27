package com.eot_app.nav_menu.drag_and_drop_functinality.model;

import java.util.List;

public class DragAndDropMapModel {
    private  String mapId;
    private  String jobId;
    private  int mapLength;
    private  int mapWidth;
    private  String mapImageUrl;
    private  String title;
    private List<MapItemModel> mapItems;
    private String createDate;
    private String updateDate;

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getMapLength() {
        return mapLength;
    }

    public void setMapLength(int mapLength) {
        this.mapLength = mapLength;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
