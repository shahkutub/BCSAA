package com.bcsaa.model;

public class CommonResponse {


    String errormsg,successmsg,data ;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

//Common data = new Common();


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


    private class Common {

    }
}
