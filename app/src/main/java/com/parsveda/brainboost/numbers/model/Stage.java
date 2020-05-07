package com.parsveda.brainboost.numbers.model;

/**
 * Created by kami on 12/19/2016.
 */
public class Stage {
    private int id;
    private String title;
    private StageType type;
    private int rating;

    public Stage() {
        this.id = 0;
        this.title = "none";
        this.type = StageType.SEQUENCED_NUMBER;
    }

    public Stage(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StageType getType() {
        return type;
    }

    public void setType(StageType type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
