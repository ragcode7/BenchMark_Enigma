package com.benchmarkChat.chatapp.TimeTable;

public class DataHolder {

    private String title;
    private String subTitle;
    private String startTime;
    private String endTime;
    private Boolean isRecess;

    public Boolean getRecess() {
        return isRecess;
    }

    public void setRecess(Boolean recess) {
        isRecess = recess;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
