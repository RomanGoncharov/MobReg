package com.romanusynin.mobreg.mobreg.objects;


public class Ticket {
    String region;
    String hospital;
    String department;
    String doctor;
    String office;
    String date;
    String time;

    public Ticket(String region, String hospital, String department, String doctor, String office, String date, String time) {
        this.region = region;
        this.hospital = hospital;
        this.department = department;
        this.doctor = doctor;
        this.office = office;
        this.date = date;
        this.time = time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
