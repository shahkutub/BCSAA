package com.bcsaa.model;

public class EmplyeeLeaveInfo {

//     "employee": "Md. Tariqul Islam",
//            "leave_type": "Earn Leave",
//            "from_date": "0000-00-00",
//            "to_date": "0000-00-00",
//            "number_of_days": 2,
//            "status": "pending"

    String employee,leave_type,from_date,to_date,number_of_days,status,action_id,remaining_leave;

    public String getRemaining_leave() {
        return remaining_leave;
    }

    public void setRemaining_leave(String remaining_leave) {
        this.remaining_leave = remaining_leave;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getNumber_of_days() {
        return number_of_days;
    }

    public void setNumber_of_days(String number_of_days) {
        this.number_of_days = number_of_days;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
