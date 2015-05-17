package com.romanusynin.mobreg.mobreg.objects;

import java.io.Serializable;

public class Doctor implements Serializable{
    private Department department;
    private String name;
    private String office;
    private String sector;
    private String specialization;
    private String id;

    public Doctor(Department department, String name, String office, String sector, String specialization, String id) {
        this.department = department;
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

    public void setId(String id) {
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

    public String getId() {
        return id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Region getRegion(){
       return department.getRegion();
    }

    public Hospital getHospital(){
        return department.hospital;
    }
}
