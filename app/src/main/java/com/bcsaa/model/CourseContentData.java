package com.bcsaa.model;

public class CourseContentData {

//    {
//            "detail_id": 26,
//            "course_name": "Law and Administration Course",
//            "batch_no": 115
//        }

    String detail_id,course_name,batch_no;

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }
}
