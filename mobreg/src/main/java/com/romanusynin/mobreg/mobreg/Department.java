package com.romanusynin.mobreg.mobreg;


import java.io.Serializable;

public class Department implements Serializable {
    String name;
    String url;
    String countTickets;

    public Department(String name, String url, String countTickets) {
        this.name = name;
        this.countTickets = countTickets;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCountTickets() {
        return countTickets;
    }
}
