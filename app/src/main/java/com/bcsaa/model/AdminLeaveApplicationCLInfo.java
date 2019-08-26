package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class AdminLeaveApplicationCLInfo {


    String leave_type_id,leave_remaining;

    List<Leave_substitute_post> leave_substitute_post = new ArrayList<>();

    public String getLeave_type_id() {
        return leave_type_id;
    }

    public void setLeave_type_id(String leave_type_id) {
        this.leave_type_id = leave_type_id;
    }

    public String getLeave_remaining() {
        return leave_remaining;
    }

    public void setLeave_remaining(String leave_remaining) {
        this.leave_remaining = leave_remaining;
    }

    public List<Leave_substitute_post> getLeave_substitute_post() {
        return leave_substitute_post;
    }

    public void setLeave_substitute_post(List<Leave_substitute_post> leave_substitute_post) {
        this.leave_substitute_post = leave_substitute_post;
    }
}
