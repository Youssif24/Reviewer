package com.saad.youssif.reviewer.model;

/**
 * Created by youssif on 13/07/17.
 */

public class Score {

    private String category;
    private String score;
    private String date;

    public Score(String category, String score, String date) {
        this.category = category;
        this.score = score;
        this.date = date;
    }

    public Score() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
