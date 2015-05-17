package com.romanusynin.mobreg.mobreg.objects;

import java.io.Serializable;

public class WorkTime implements Serializable {
    private WorkDay workDay;
    private String time;
    private String url;

    public WorkTime(WorkDay workDay, String time, String url) {
        this.workDay = workDay;
        this.time = time;
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public String getRegionName(){
        return workDay.getRegion().getName();
    }

    public String getHospitalName(){
        return workDay.getHospital().getName();
    }

    public String getDepartmentName(){
        return workDay.getDepartment().getName();
    }

    public String getDoctorName(){
        return workDay.getDoctorName();
    }

    public String getDoctorSpec(){
        return workDay.getDoctorSpec();
    }

    public String getDoctorOffice(){
        return workDay.getDoctor().getOffice();
    }

    public String getWorkDate(){
        return workDay.getDate();
    }

}
