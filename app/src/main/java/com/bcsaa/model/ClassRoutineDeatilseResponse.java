package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class ClassRoutineDeatilseResponse {

//    {
//    "errormsg": "Data not found",
//    "successmsg": "Data has been found",
//    "data": [
//        {
//            "course_name": "Law and Administration Course",
//            "batch_no": 110,
//            "date": "26-02-2019",
//            "grouprandom": "155107314026"
//        },
//        {
//            "course_name": "Law and Administration Course",
//            "batch_no": 110,
//            "date": "12-05-2019",
//            "grouprandom": "155751042033"
//        }
//    ]
//}

    String errormsg,successmsg;

    List<ClassRoutineDetailseInfo> data = new ArrayList<>();

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

    public List<ClassRoutineDetailseInfo> getData() {
        return data;
    }

    public void setData(List<ClassRoutineDetailseInfo> data) {
        this.data = data;
    }
}
