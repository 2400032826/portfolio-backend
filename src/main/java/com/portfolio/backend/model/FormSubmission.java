package com.portfolio.backend.model;

public class FormSubmission {
    private String name;
    private String email;
    private String answerOne;
    private String answerTwo;

    public FormSubmission() {}

    public FormSubmission(String name, String email, String answerOne, String answerTwo) {
        this.name = name;
        this.email = email;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }
}