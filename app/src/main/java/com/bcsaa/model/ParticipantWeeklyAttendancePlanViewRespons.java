package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantWeeklyAttendancePlanViewRespons {

    List<ParticipantWeeklyAttendancePlanViewData> data = new ArrayList<>();

    public List<ParticipantWeeklyAttendancePlanViewData> getData() {
        return data;
    }

    public void setData(List<ParticipantWeeklyAttendancePlanViewData> data) {
        this.data = data;
    }

}
