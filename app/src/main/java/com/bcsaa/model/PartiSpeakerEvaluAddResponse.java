package com.bcsaa.model;

public class PartiSpeakerEvaluAddResponse {

    SpeakerEvalutAddData data = new SpeakerEvalutAddData();

    Exitspeakereveluation exitspeakereveluation = new Exitspeakereveluation();

    public Exitspeakereveluation getExitspeakereveluation() {
        return exitspeakereveluation;
    }

    public void setExitspeakereveluation(Exitspeakereveluation exitspeakereveluation) {
        this.exitspeakereveluation = exitspeakereveluation;
    }

    public SpeakerEvalutAddData getData() {
        return data;
    }

    public void setData(SpeakerEvalutAddData data) {
        this.data = data;
    }
}
