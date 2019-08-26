package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class AdminleaveapplicationelInfo {


    List<Leave_substitute_post> leave_substitute_post = new ArrayList<>();
    Maternity Maternity = new Maternity();
    Earn_Leave Earn_Leave = new Earn_Leave();

    public com.bcsaa.model.Maternity getMaternity() {
        return Maternity;
    }

    public void setMaternity(com.bcsaa.model.Maternity maternity) {
        Maternity = maternity;
    }

    public com.bcsaa.model.Earn_Leave getEarn_Leave() {
        return Earn_Leave;
    }

    public void setEarn_Leave(com.bcsaa.model.Earn_Leave earn_Leave) {
        Earn_Leave = earn_Leave;
    }

    public List<Leave_substitute_post> getLeave_substitute_post() {
        return leave_substitute_post;
    }

    public void setLeave_substitute_post(List<Leave_substitute_post> leave_substitute_post) {
        this.leave_substitute_post = leave_substitute_post;
    }
}
