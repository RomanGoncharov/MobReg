package com.romanusynin.mobreg.mobreg.objects;


import java.io.Serializable;

public class Hospital implements Serializable {
    private Region region;
    private String name;
    private String url;
    private String address;
    private String numberPhone;

    Hospital(Region region, String name, String url, String address){
        this.region = region;
        this.name = name;
        this.url = url;
        this.address = address;
        this.numberPhone = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getUrl() {
        return url;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
