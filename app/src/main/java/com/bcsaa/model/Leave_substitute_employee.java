package com.bcsaa.model;

public class Leave_substitute_employee {

    String leave_substitute_id,leave_substitute_name;

    public String getLeave_substitute_id() {
        return leave_substitute_id;
    }

    public void setLeave_substitute_id(String leave_substitute_id) {
        this.leave_substitute_id = leave_substitute_id;
    }

    public String getLeave_substitute_name() {
        return leave_substitute_name;
    }

    public void setLeave_substitute_name(String leave_substitute_name) {
        this.leave_substitute_name = leave_substitute_name;
    }


    @Override
    public String toString() {
        return leave_substitute_name;
    }
}
