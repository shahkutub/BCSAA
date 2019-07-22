package com.bcsaa.model;

public class ParticipantExamScheduleData {

//      "dateandtime": "07-14-2019 12:00 pm - 01:00 pm",
//            "module_name": "Class Test on Manner, Etiquettes and Protocol",
//            "speaker_name": "Course Management Team(CMT)",
//            "building_name": "Main Building",
//            "venue_name": "Prayer Room"

    String dateandtime,module_name,speaker_name,building_name,venue_name;

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getSpeaker_name() {
        return speaker_name;
    }

    public void setSpeaker_name(String speaker_name) {
        this.speaker_name = speaker_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }
}
