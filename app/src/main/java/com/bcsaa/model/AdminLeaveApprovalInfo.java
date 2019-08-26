package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class AdminLeaveApprovalInfo {


    String applicant_name,department,designation,leave_substitute,leave_type,leave_application_id,number_of_days,leave_remaining;

    List<Leave_substitute_post> leave_substitute_post = new ArrayList<>();


    public List<Leave_substitute_post> getLeave_substitute_post() {
        return leave_substitute_post;
    }

    public void setLeave_substitute_post(List<Leave_substitute_post> leave_substitute_post) {
        this.leave_substitute_post = leave_substitute_post;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLeave_substitute() {
        return leave_substitute;
    }

    public void setLeave_substitute(String leave_substitute) {
        this.leave_substitute = leave_substitute;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public String getLeave_application_id() {
        return leave_application_id;
    }

    public void setLeave_application_id(String leave_application_id) {
        this.leave_application_id = leave_application_id;
    }

    public String getNumber_of_days() {
        return number_of_days;
    }

    public void setNumber_of_days(String number_of_days) {
        this.number_of_days = number_of_days;
    }

    public String getLeave_remaining() {
        return leave_remaining;
    }

    public void setLeave_remaining(String leave_remaining) {
        this.leave_remaining = leave_remaining;
    }

}
