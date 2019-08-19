package com.bcsaa.model;

public class CommonResponse {


    String errormsg,successmsg;


    CommonData data = new CommonData();

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

    public CommonData getData() {
        return data;
    }

    public void setData(CommonData data) {
        this.data = data;
    }
}
