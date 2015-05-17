package com.romanusynin.mobreg.mobreg.objects;


import java.io.Serializable;

public class WorkDay implements Serializable{
    private Doctor doctor;
    private String date;
    private String workTimeInterval;
    private String freeTalons;
    private String url;
    private String prevWorkDayUrl;
    private String nextWorkDayUrl;

    public WorkDay(Doctor doctor,String date, String workTimeInterval, String url, String freeTalons) {
        this.doctor = doctor;
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

    public String getDate() { return date;}

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

    public String getPrevWorkDayUrl() {
        return prevWorkDayUrl;
    }

    public void setPrevWorkDayUrl(String prevWorkDayUrl) {
        this.prevWorkDayUrl = prevWorkDayUrl;
    }

    public String getNextWorkDayUrl() {
        return nextWorkDayUrl;
    }

    public void setNextWorkDayUrl(String nextWorkDayUrl) {
        this.nextWorkDayUrl = nextWorkDayUrl;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Region getRegion(){
        return doctor.getRegion();
    }

    public Hospital getHospital(){
        return doctor.getHospital();
    }

    public Department getDepartment(){
        return doctor.getDepartment();
    }

    public String getDoctorName(){
        return doctor.getName();
    }

    public String getDoctorSpec(){
        return doctor.getSpecialization();
    }
}
