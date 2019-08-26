package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLeaveListResponse {


    List<EmplyeeLeaveInfo> data = new ArrayList<>();

    public List<EmplyeeLeaveInfo> getData() {
        return data;
    }

    public void setData(List<EmplyeeLeaveInfo> data) {
        this.data = data;
    }
}
