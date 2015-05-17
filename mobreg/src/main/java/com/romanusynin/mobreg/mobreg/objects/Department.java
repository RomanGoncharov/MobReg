package com.romanusynin.mobreg.mobreg.objects;


import java.io.Serializable;

public class Department implements Serializable {
    Hospital hospital;
    String name;
    String url;
    String countTickets;

    public Department(Hospital hospital, String name, String url, String countTickets) {
        this.hospital = hospital;
        this.name = name;
        this.countTickets = countTickets;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCountTickets(String countTickets) {
        this.countTickets = countTickets;
    }

    public String getCountTickets() {
        return countTickets;
    }

    public Region getRegion(){
        return hospital.getRegion();
    }
}
