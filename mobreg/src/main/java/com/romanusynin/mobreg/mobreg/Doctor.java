package com.romanusynin.mobreg.mobreg;

public class Doctor {
    private String name;
    private String office;
    private String sector;
    private String specialization;
    private int id;

    public Doctor(String name, String office, String sector, String specialization, int id) {
        this.name = name;
        this.office = office;
        this.sector = sector;
        this.specialization = specialization;
        this.id = id;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
