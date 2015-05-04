package com.romanusynin.mobreg.mobreg;


import java.io.Serializable;

public class Hospital implements Serializable {
    private String name;
    private String url;
    private String address;
    private String numberPhone;

    Hospital(String name, String url, String address){
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
}
