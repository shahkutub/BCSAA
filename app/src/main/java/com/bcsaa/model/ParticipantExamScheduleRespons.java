package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantExamScheduleRespons {

    List<ParticipantExamScheduleData> data = new ArrayList<>();

    public List<ParticipantExamScheduleData> getData() {
        return data;
    }

    public void setData(List<ParticipantExamScheduleData> data) {
        this.data = data;
    }
}
