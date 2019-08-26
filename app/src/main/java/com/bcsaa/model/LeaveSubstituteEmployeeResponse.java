package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class LeaveSubstituteEmployeeResponse {

    List<Leave_substitute_employee> leave_substitute = new ArrayList<>();

    public List<Leave_substitute_employee> getLeave_substitute() {
        return leave_substitute;
    }

    public void setLeave_substitute(List<Leave_substitute_employee> leave_substitute) {
        this.leave_substitute = leave_substitute;
    }
}
