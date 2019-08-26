package com.bcsaa.model;

public class Leave_substitute_post {


    String academic_designation_id,designation_name_en;

    public String getAcademic_designation_id() {
        return academic_designation_id;
    }

    public void setAcademic_designation_id(String academic_designation_id) {
        this.academic_designation_id = academic_designation_id;
    }

    public String getDesignation_name_en() {
        return designation_name_en;
    }

    public void setDesignation_name_en(String designation_name_en) {
        this.designation_name_en = designation_name_en;
    }

    @Override
    public String toString() {
        return designation_name_en;
    }
}


