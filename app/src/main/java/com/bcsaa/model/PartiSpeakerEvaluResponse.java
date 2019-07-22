package com.bcsaa.model;

import java.util.ArrayList;
import java.util.List;

public class PartiSpeakerEvaluResponse {

    String errormsg,successmsg;

    List<SpeakerEvaluData> data = new ArrayList<>();

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

    public List<SpeakerEvaluData> getData() {
        return data;
    }

    public void setData(List<SpeakerEvaluData> data) {
        this.data = data;
    }
}
