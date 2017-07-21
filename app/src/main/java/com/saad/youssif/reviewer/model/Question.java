package com.saad.youssif.reviewer.model;

/**
 * Created by youssif on 06/07/17.
 */

public class Question {

    private String question;
    private String ch1,ch2,ch3,ch4;
    private String answer;
    private int id;

    public Question() {
    }

    public Question(int id,String question, String ch1,String ch2,String ch3,String ch4, String answer) {
        this.id=id;
        this.question = question;
        this.ch1 = ch1;
        this.ch2= ch2;
        this.ch3 = ch3;
        this.ch4 = ch4;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCh1() {
        return ch1;
    }

    public void setCh1(String ch1) {
        this.ch1 = ch1;
    }

    public String getCh2() {
        return ch2;
    }

    public void setCh2(String ch2) {
        this.ch2 = ch2;
    }

    public String getCh3() {
        return ch3;
    }

    public void setCh3(String ch3) {
        this.ch3 = ch3;
    }

    public String getCh4() {
        return ch4;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
