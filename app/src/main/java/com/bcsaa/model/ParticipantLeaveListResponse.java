package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantLeaveListResponse {

    List<LeaveInfo> data = new ArrayList<>();

    public List<LeaveInfo> getData() {
        return data;
    }

    public void setData(List<LeaveInfo> data) {
        this.data = data;
    }
}
