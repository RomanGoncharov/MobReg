package com.romanusynin.mobreg.mobreg;


public class WorkDay {
    private String date;
    private String workTimeInterval;
    private String freeTalons;
    private String url;

    public WorkDay(String date, String workTimeInterval, String url, String freeTalons) {
        this.date = date;
        this.workTimeInterval = workTimeInterval;
        this.url = url;
        this.freeTalons = freeTalons;
    }

    public String getFreeTalons() {
        return freeTalons;
    }

    public void setFreeTalons(String freeTalons) {
        this.freeTalons = freeTalons;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkTimeInterval() {
        return workTimeInterval;
    }

    public void setWorkTimeInterval(String workTimeInterval) {
        this.workTimeInterval = workTimeInterval;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
