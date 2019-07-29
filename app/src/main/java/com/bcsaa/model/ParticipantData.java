package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantData {

    String course,batch,male_dress,female_dress;

    List<DailyDchedule> daily_schedule = new ArrayList<>();
    List<CmtInfo> cmt = new ArrayList<>();

    public List<CmtInfo> getCmt() {
        return cmt;
    }

    public void setCmt(List<CmtInfo> cmt) {
        this.cmt = cmt;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMale_dress() {
        return male_dress;
    }

    public void setMale_dress(String male_dress) {
        this.male_dress = male_dress;
    }

    public String getFemale_dress() {
        return female_dress;
    }

    public void setFemale_dress(String female_dress) {
        this.female_dress = female_dress;
    }

    public List<DailyDchedule> getDaily_schedule() {
        return daily_schedule;
    }

    public void setDaily_schedule(List<DailyDchedule> daily_schedule) {
        this.daily_schedule = daily_schedule;
    }
}
