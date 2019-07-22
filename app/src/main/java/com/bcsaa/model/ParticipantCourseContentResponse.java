package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ParticipantCourseContentResponse {

//    {
//    "errormsg": "Course Content not found",
//    "successmsg": "Course Content has been found",
//    "data": [
//        {
//            "detail_id": 26,
//            "course_name": "Law and Administration Course",
//            "batch_no": 115
//        }
//    ]
//}

    String errormsg,successmsg;

    List<CourseContentData> data = new ArrayList<>();

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getSuccessmsg() {
        return successmsg;
    }

    public void setSuccessmsg(String successmsg) {
        this.successmsg = successmsg;
    }

    public List<CourseContentData> getData() {
        return data;
    }

    public void setData(List<CourseContentData> data) {
        this.data = data;
    }
}
