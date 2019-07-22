package com.bcsaa.model;

public class SpeakerEvaluData {

//    "course_name": "Law and Administration Course",
//            "batch_name": 115,
//            "module_name": "Manner, Etiquettes and Protocol",
//            "session_name": "Introducing Self: Get-up: Dress/Attire, Stance Introducing: In person and over telephone",
//            "speakertype_name": "BCS Speaker",
//            "user_name": "A K M Sohel",
//            "session_id": 987

    String course_name,batch_name,module_name,session_name,speakertype_name,user_name,session_id;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSpeakertype_name() {
        return speakertype_name;
    }

    public void setSpeakertype_name(String speakertype_name) {
        this.speakertype_name = speakertype_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
