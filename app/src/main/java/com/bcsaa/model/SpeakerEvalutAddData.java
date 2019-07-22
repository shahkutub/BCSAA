package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class SpeakerEvalutAddData {

//     "course_name": "Law and Administration Course",
//        "module_name": "Manner, Etiquettes and Protocol",
//        "speaker_name": "A K M Sohel",
//        "speaker_id": 21,
//        "speaker_type_id": 1,
//        "batch_no": 115,
//        "session_name": "Introducing Self: Get-up: Dress/Attire, Stance Introducing: In person and over telephone",
//        "session_id": 987,

    String course_name,module_name,speaker_name,speaker_id,speaker_type_id,batch_no,session_name,session_id;

    List<Evaluation> evaluation = new ArrayList<>();

    public List<Evaluation> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(List<Evaluation> evaluation) {
        this.evaluation = evaluation;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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

    public String getSpeaker_id() {
        return speaker_id;
    }

    public void setSpeaker_id(String speaker_id) {
        this.speaker_id = speaker_id;
    }

    public String getSpeaker_type_id() {
        return speaker_type_id;
    }

    public void setSpeaker_type_id(String speaker_type_id) {
        this.speaker_type_id = speaker_type_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
