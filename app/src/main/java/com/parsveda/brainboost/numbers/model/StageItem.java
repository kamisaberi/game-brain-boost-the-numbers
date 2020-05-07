package com.parsveda.brainboost.numbers.model;

import android.graphics.Bitmap;

public class StageItem {
    private Bitmap image;
    private String title;
    private int id;
    private StageType type;
    private StageTargetType targetType;
    private int chances;
    private int target;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }

    public StageTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(StageTargetType targetType) {
        this.targetType = targetType;
    }

    public StageType getType() {
        return type;
    }

    public void setType(StageType type) {
        this.type = type;
    }

    public StageItem() {
        title = "";
        id = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public StageItem(String title) {
        super();
        this.title = title;
    }

    public StageItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
