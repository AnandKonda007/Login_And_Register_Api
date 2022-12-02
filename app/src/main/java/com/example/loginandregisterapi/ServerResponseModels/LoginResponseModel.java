package com.example.loginandregisterapi.ServerResponseModels;

public class LoginResponseModel {
    private int  response;
    private String message;
    private String jsontoken;
    private InfoModel Info;

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJsontoken() {
        return jsontoken;
    }

    public void setJsontoken(String jsontoken) {
        this.jsontoken = jsontoken;
    }

    public InfoModel getInfo() {
        return Info;
    }

    public void setInfo(InfoModel info) {
        Info = info;
    }
}
