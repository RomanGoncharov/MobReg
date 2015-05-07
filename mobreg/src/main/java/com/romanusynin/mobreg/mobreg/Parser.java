package com.romanusynin.mobreg.mobreg;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static ArrayList<Region> getRegions() {
        try {
            Document doc = Jsoup.connect(Constants.REGIONS_URL).timeout(Constants.TIMEOUT).get();
            ArrayList<Region> regionsArrayList = new ArrayList<Region>();
            Elements regionsElements = doc.select("#city_list").get(0).select("li");
            for (int i=2; i<regionsElements.size(); i++)
            {
                String url = regionsElements.get(i).select("li").get(0).select("a").get(0).attr("href");
                String name = regionsElements.get(i).select("li").get(0).select("a").get(0).select("span").get(0).childNodes().get(0).toString();
                String countHospital = regionsElements.get(i).select("li").get(0).select("a").get(0).select("span").get(0).childNodes().get(1).childNode(0).toString();
                Region region = new Region(name, url, countHospital);
                regionsArrayList.add(region);
            }
            return regionsArrayList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Hospital> getHospitals(String urlRegion) {
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN+urlRegion).timeout(Constants.TIMEOUT).get();
            ArrayList<Hospital> hospitalsArrayList = new ArrayList<Hospital>();
            Elements hospitalsElements = doc.select("#pol_list").get(0).select("li");
            for (int i=0;i<hospitalsElements.size(); i++){
                if (hospitalsElements.get(i).select("ul").size() == 0){
                    String url = hospitalsElements.get(i).select("a").get(0).attr("href");
                    String name = hospitalsElements.get(i).select("span").get(0).childNode(0).toString();
                    String address = hospitalsElements.get(i).select("span").get(1).text();
                    Hospital hospital = new Hospital(name, url, address);
                    hospitalsArrayList.add(hospital);
                }
                else{
                    continue;
                }
            }
            return hospitalsArrayList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Department> getDepartments(String urlHospital) {
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN+urlHospital).timeout(Constants.TIMEOUT).get();
            ArrayList<Department> departmentArrayList = new ArrayList<Department>();
            Elements departmentsElements = doc.select("#spec_list").get(0).select("li");
            for (int i=0;i<departmentsElements.size(); i+=2){
                String url = departmentsElements.get(i+1).select("a").get(0).attr("href");
                String name = departmentsElements.get(i).select("span").get(0).childNode(2).toString().substring(1);
                String countTickets = departmentsElements.get(i).select("span").get(2).text();
                Department department = new Department(name, url, countTickets );
                departmentArrayList.add(department);
            }
            return departmentArrayList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHospitalPhone(String urlHospital){
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN + urlHospital).timeout(Constants.TIMEOUT).get();
            if (doc.select(".explanation_step").get(0).childNodes().size() == 1 || doc.select(".explanation_step").get(0).childNode(2).toString().length() == 1) {
                return null;
            }
            return doc.select(".explanation_step").get(0).childNode(2).toString().split(" ")[2];
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static ArrayList<Doctor> getDoctors(String urlDepartment){
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN + urlDepartment).timeout(Constants.TIMEOUT).get();
            ArrayList<Doctor> doctorsArrayList = new ArrayList<Doctor>();
            Elements doctorsElements = doc.select(".list_choose_doc");
            for (int i=0;i<doctorsElements.size(); i++){
                String[] doctorBasicStr = doctorsElements.get(i).select(".table_doctor_line").text().split(" ");
                String name = doctorBasicStr[0]+" "+doctorBasicStr[1]+" "+doctorBasicStr[2];
                String specialization = doctorsElements.get(i).select(".table_doctor_line").get(0).select("span").text();
                String office = doctorsElements.get(i).select(".kabinet").text();
                String id;
                Elements docIdS = doctorsElements.get(i).select("input[name=pcod]");
                if (docIdS.size() > 0){
                    id = docIdS.get(0).val();
                }
                else{
                    id = null;
                }
                if (office.length() > 0){
                    office = "Кабинет № " + office;
                }
                String sector = doctorsElements.get(i).select(".table_doctor_room").get(0).text();
                Doctor doctor = new Doctor(name, office, sector, specialization, id);
                doctorsArrayList.add(doctor);
            }
            return doctorsArrayList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static class WorkDaysAndWeek{
        ArrayList <WorkDay> workDays;
        String week;

        public WorkDaysAndWeek(ArrayList<WorkDay> workDays, String week) {
            this.workDays = workDays;
            this.week = week;
        }

        public ArrayList<WorkDay> getWorkDays() {
            return workDays;
        }

        public String getWeek() {
            return week;
        }
    }

    public static WorkDaysAndWeek getWorkDaysAndWeek(String urlDepartment, Doctor doctor, int weekNumber){
        try {
            String formatedUrl = urlDepartment.substring(0, urlDepartment.length()-1)+ Integer.toString(weekNumber);
            Document doc = Jsoup.connect(Constants.DOMAIN + formatedUrl).timeout(Constants.TIMEOUT).get();
            ArrayList<WorkDay> workDaysArrayList = new ArrayList<WorkDay>();
            Elements workDaysElements = new Elements();
            String doctor_id = doctor.getId();
            if (doctor_id != null){
                workDaysElements = doc.select(".list_choose_time").
                        select(".time_table_green_step4").select("input[value="+doctor_id+"]");
            }
            else{
                Elements doctorsElements = doc.select(".list_choose_doc");
                for (int i=0;i<doctorsElements.size(); i++) {
                    String[] doctorBasicStr = doctorsElements.get(i).select(".table_doctor_line").text().split(" ");
                    String doctor_name = doctorBasicStr[0]+" "+doctorBasicStr[1]+" "+doctorBasicStr[2];
                    if (doctor_name.equals(doctor.getName())){
                        workDaysElements = doctorsElements.get(i).select(".list_choose_time").select(".time_table_green_step4");
                        if (workDaysElements.size() > 0){
                            doctor.setId(workDaysElements.get(0).select("input[name=pcod]").val());
                        }
                    }
                }
            }
            for (int i=0;i<workDaysElements.size(); i++){
                Element basicElement = (Element) workDaysElements.get(i).parentNode();
                String date = basicElement.select("input[name=date]").get(0).attr("value");
                List<Node> timeElements = basicElement.select(".date_label").get(0).childNodes();
                String workTimeInterval = timeElements.get(0)+":"+timeElements.get(1).childNode(0).toString()+
                        timeElements.get(2)+":" + timeElements.get(3).childNode(0).toString();
                String freeTalons = "свободно талонов: " + timeElements.get(4).childNode(0).toString();
                String url = basicElement.select("input[name=href]").get(0).attr("value");
                WorkDay workDay = new WorkDay(date, workTimeInterval, url, freeTalons);
                workDaysArrayList.add(workDay);
            }
            String current_week = doc.select(".current_week").get(0).text();
            return new WorkDaysAndWeek(workDaysArrayList, current_week);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
