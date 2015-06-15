package com.romanusynin.mobreg.mobreg.objects;


import com.squareup.okhttp.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


    // Returned Objects of methods Parser

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

    public static class WorkTimesListAndCookieObject{
        ArrayList <WorkTime> workTimes;
        String cookie;

        public WorkTimesListAndCookieObject(ArrayList<WorkTime> workTimes, String cookie) {
            this.workTimes = workTimes;
            this.cookie = cookie;
        }

        public ArrayList<WorkTime> getWorkTimes() {
            return workTimes;
        }

        public String getCookie() {return cookie;}
    }

    public static class ResponseObject{

        boolean success;
        ArrayList<Ticket> tickets;
        String errorMessage;

        public ResponseObject(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public ResponseObject(boolean success, ArrayList<Ticket> tickets) {
            this.success = success;
            this.tickets = tickets;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public ArrayList<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(ArrayList<Ticket> tickets) {
            this.tickets = tickets;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }


    // Methods of Parser

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

    public static ArrayList<Hospital> getHospitals(Region region) {
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN+region.getUrl()).timeout(Constants.TIMEOUT).get();
            ArrayList<Hospital> hospitalsArrayList = new ArrayList<Hospital>();
            Elements hospitalsElements = doc.select("#pol_list").get(0).select("li");
            for (int i=0;i<hospitalsElements.size(); i++){
                if (hospitalsElements.get(i).select("ul").size() == 0){
                    String url = hospitalsElements.get(i).select("a").get(0).attr("href");
                    String name = hospitalsElements.get(i).select("span").get(0).childNode(0).toString();
                    String address = hospitalsElements.get(i).select("span").get(1).text();
                    Hospital hospital = new Hospital(region, name, url, address);
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

    public static ArrayList<Department> getDepartmentsList(Hospital hospital) {
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN + hospital.getUrl()).timeout(Constants.TIMEOUT).get();
            ArrayList<Department> departmentArrayList = new ArrayList<Department>();
            Elements departmentsElements = doc.select("#spec_list").get(0).select("li");
            for (int i=0;i<departmentsElements.size(); i+=2){
                String url = departmentsElements.get(i+1).select("a").get(0).attr("href");
                String name = departmentsElements.get(i).select("span").get(0).childNode(2).toString().substring(1);
                String countTickets = departmentsElements.get(i).select("span").get(2).text();
                Department department = new Department(hospital, name, url, countTickets );
                departmentArrayList.add(department);
            }
            String hospitalPhone;
            if (doc.select(".explanation_step").get(0).childNodes().size() == 1 || doc.select(".explanation_step").get(0).childNode(2).toString().length() == 1) {
                hospitalPhone = null;
            }
            else{
                hospitalPhone = doc.select(".explanation_step").get(0).childNode(2).toString().split(" ")[2];
            }
            hospital.setNumberPhone(hospitalPhone);
            return departmentArrayList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Doctor> getDoctors(Department department){
        try {
            Document doc = Jsoup.connect(Constants.DOMAIN + department.getUrl()).timeout(Constants.TIMEOUT).get();
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
                Doctor doctor = new Doctor(department, name, office, sector, specialization, id);
                doctorsArrayList.add(doctor);
            }
            return doctorsArrayList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static WorkDaysAndWeek getWorkDaysAndWeek(Doctor doctor, int weekNumber){
        try {
            String urlDepartment = doctor.getDepartment().getUrl();
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
                WorkDay workDay = new WorkDay(doctor,date, workTimeInterval, url, freeTalons);
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

    public static WorkTimesListAndCookieObject getWorkDateTimes(WorkDay workDay, int flag){ // flag 0 - none, 1-prev , 2-next
        try {
            String workDayUrl;
            if (flag ==0){
                workDayUrl =  workDay.getUrl();
            }
            else if (flag == 1){
                workDayUrl = workDay.getPrevWorkDayUrl();
            }
            else{
                workDayUrl = workDay.getNextWorkDayUrl();
            }
            Connection.Response res = Jsoup.connect(Constants.DOMAIN + workDayUrl).timeout(Constants.TIMEOUT).execute();
            String cookie = "PHPSESSID="+res.cookies().get("PHPSESSID");
            Document doc = res.parse();
            ArrayList <WorkTime> ticketsArrayList = new ArrayList<WorkTime>();
            Elements ticketsElements = doc.select(".green_button_time");
            for (int i=0;i<ticketsElements.size(); i++) {
                String formatedUrlWorkday = workDay.getUrl().substring(0, 14)+"6"+ workDay.getUrl().substring(15, workDay.getUrl().length());
                String url = formatedUrlWorkday+"/"+ticketsElements.get(i).select(".id").text();
                String time = ticketsElements.get(i).select("span").get(0).text();
                WorkTime workTime = new WorkTime(workDay, time, url);
                ticketsArrayList.add(workTime);
            }
            String current_date = doc.select(".current_day").text();
            String prevDayUrl = null;
            if (doc.select(".back_week").attr("style").length()==0){
                prevDayUrl = workDay.getUrl().substring(0, workDay.getUrl().length()-10) + doc.select(".back_week").attr("href");
            }
            String nextDayUrl = workDay.getUrl().substring(0, workDay.getUrl().length()-10) + doc.select(".next_week").attr("href");
            workDay.setPrevWorkDayUrl(prevDayUrl);
            workDay.setNextWorkDayUrl(nextDayUrl);
            workDay.setDate(current_date);
            return new WorkTimesListAndCookieObject(ticketsArrayList, cookie);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseObject sendAndParseResponse(String url, String n_polisa, String birthday, String cookie){
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormEncodingBuilder()
                    .add("s_polisa","")
                    .add("n_polisa", n_polisa)
                    .add("birthday", birthday)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Cookie", cookie)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseHTML = response.body().string();
            Document doc = Jsoup.parse(responseHTML);
            String script = doc.select("#content").select("script").get(0).html();
            Pattern p = Pattern.compile("\\$\\(\\'#\\w*\\'\\).find\\(\\'.title_popup_red\\'\\).text\\(\\'([а-яА-Я0-9 ]*)\\'\\)");
            Matcher m = p.matcher(script);
            if (m.find()){
                String error_message = m.group(1);
                return new ResponseObject(false, error_message);
            }
            else {
                ArrayList<Ticket> myTickets = new ArrayList<Ticket>();
                Elements ticketsElements = doc.select(".green_talon");
                String region = doc.select(".oldInfBlack").get(0).childNode(0).toString();
                String hospital = doc.select(".oldInfBlack").get(0).childNode(2).toString();
                for (Element ticketsElement: ticketsElements){
                    String department = ticketsElement.select(".top_green_talon").text();
                    String last_name = ticketsElement.select(".border_talon_1").get(0).childNode(2).toString();
                    String first_name = ticketsElement.select(".border_talon_1").get(0).childNode(4).toString();
                    String doctor = last_name + first_name;
                    String date  = ticketsElement.select(".border_talon_2").get(0).childNode(3).toString();
                    String time = ticketsElement.select(".border_talon_2").select(".time_priema").get(0).
                            childNode(3).childNode(0).toString();
                    String office = ticketsElement.select(".border_talon_2").select(".cabinet").get(0).
                            childNode(4).toString();
                    Ticket ticket = new Ticket(region, hospital, department, doctor, office, date, time);
                    myTickets.add(ticket);
                }
                return new ResponseObject(true, myTickets);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
