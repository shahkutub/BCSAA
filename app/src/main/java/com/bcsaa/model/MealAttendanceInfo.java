package com.bcsaa.model;

public class MealAttendanceInfo {

//    "date": "2019-07-21",
//                        "week": "0",
//                        "meal_time_id": 1,
//                        "attend": "yes"

    String date,week,meal_time_id,attend;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMeal_time_id() {
        return meal_time_id;
    }

    public void setMeal_time_id(String meal_time_id) {
        this.meal_time_id = meal_time_id;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }
}
