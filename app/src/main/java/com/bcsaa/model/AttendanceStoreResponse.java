package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class AttendanceStoreResponse {


    String successmsg;
    List<AttendanceStoreDat> data = new ArrayList<>();

    public String getSuccessmsg() {
        return successmsg;
    }

    public void setSuccessmsg(String successmsg) {
        this.successmsg = successmsg;
    }

    public List<AttendanceStoreDat> getData() {
        return data;
    }

    public void setData(List<AttendanceStoreDat> data) {
        this.data = data;
    }
}
