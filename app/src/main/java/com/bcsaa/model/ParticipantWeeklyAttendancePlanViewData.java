package com.bcsaa.model;

public class ParticipantWeeklyAttendancePlanViewData {

    String month_week,week_id,week_lenth,week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    ActionData action = new ActionData();

    public String getMonth_week() {
        return month_week;
    }

    public void setMonth_week(String month_week) {
        this.month_week = month_week;
    }

    public ActionData getAction() {
        return action;
    }

    public void setAction(ActionData action) {
        this.action = action;
    }

    public String getWeek_id() {
        return week_id;
    }

    public void setWeek_id(String week_id) {
        this.week_id = week_id;
    }

    public String getWeek_lenth() {
        return week_lenth;
    }

    public void setWeek_lenth(String week_lenth) {
        this.week_lenth = week_lenth;
    }
}
