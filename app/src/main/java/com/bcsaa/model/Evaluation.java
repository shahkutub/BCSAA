package com.bcsaa.model;

public class Evaluation {

//    "parameter_name": "Knowledge of the Subject",
//                "parameter_id": 1,
//                "total_marks": 10

    String parameter_name,parameter_id,total_marks;

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    public String getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(String parameter_id) {
        this.parameter_id = parameter_id;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }
}
