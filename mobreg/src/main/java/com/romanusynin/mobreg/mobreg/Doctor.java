package com.romanusynin.mobreg.mobreg;

public class Doctor {
    private String name;
    private String office;
    private String sector;
    private int id;

    public Doctor(String name, String office, int id, String sector) {
        this.name = name;
        this.office = office;
        this.id = id;
        this.sector = sector;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getName() {
        return name;
    }

    public String getOffice() {
        return office;
    }

    public String getSector() {
        return sector;
    }

    public int getId() {
        return id;
    }
}
