package com.example.myapplication;

public class message {

    private String userMessage;
    private String gptResponse;

    public message() {
    }

    public message(String userMessage, String gptResponse) {
        this.userMessage = userMessage;
        this.gptResponse = gptResponse;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getGptResponse() {
        return gptResponse;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public void setGptResponse(String gptResponse) {
        this.gptResponse = gptResponse;
    }
}
