package com.parsveda.brainboost.numbers.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kami on 12/18/2016.
 */
public class Preset {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<ViewModel> models;

    public List<ViewModel> getModels() {
        return models;
    }

    public void setModels(List<ViewModel> models) {
        this.models = models;
    }

    public Preset() {
        this.models = new ArrayList<>();
    }
}
