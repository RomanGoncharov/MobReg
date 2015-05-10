package com.romanusynin.mobreg.mobreg.objects;

import java.io.Serializable;

public class Region implements Serializable {
    private String name;
    private String url;
    private String countHospital;

    Region(String name, String url, String countHospital){
        this.name = name;
        this.url = url;
        this.countHospital = countHospital;

    }

    public String getName(){
        return this.name;
    }

    public String getUrl(){
        return this.url;
    }

    public String getCountHospital(){
        return this.countHospital;
    }
}
